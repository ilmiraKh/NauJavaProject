package ru.khamitova.NauJavaProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khamitova.NauJavaProject.entity.Report;
import ru.khamitova.NauJavaProject.entity.Topic;
import ru.khamitova.NauJavaProject.entity.enums.ReportStatus;
import ru.khamitova.NauJavaProject.repository.ReportRepository;
import ru.khamitova.NauJavaProject.repository.TopicRepository;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.service.ReportService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, TopicRepository topicRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public String getText(Long id){
        return getById(id).getText();
    }

    public Long createReport(){
        Report r = new Report();
        r.setText("Report created, but it's text is not generated yet. Please wait");
        r.setStatus(ReportStatus.CREATED);
        return reportRepository.save(r).getId();
    }

    public void generateReport(Long id){
        CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();

            Report report = getById(id);

            try {
                AtomicLong userCount = new AtomicLong();
                AtomicLong userTime = new AtomicLong();
                AtomicReference<List<Topic>> topics = new AtomicReference<>();
                AtomicLong topicTime = new AtomicLong();

                Thread t1 = new Thread(() -> {
                    try {
                        long userStartTime = System.currentTimeMillis();
                        userCount.set(userRepository.count());
                        userTime.set(System.currentTimeMillis() - userStartTime);

                    } catch (Exception e) {
                        throw new RuntimeException("Error counting users: " + e.getMessage());
                    }
                });

                Thread t2 = new Thread(() -> {
                    try {
                        long topicStartTime = System.currentTimeMillis();
                        topics.set((List<Topic>)topicRepository.findAll());
                        topicTime.set(System.currentTimeMillis() - topicStartTime);
                    } catch (Exception e) {
                        throw new RuntimeException("Error getting topics: " + e.getMessage());
                    }
                });

                t1.start();
                t2.start();

                t1.join();
                t2.join();

                long totalTime = System.currentTimeMillis() - startTime;
                String reportText = reportText(userCount.get(), userTime.get(),
                        topicTime.get(), topics.get(), totalTime);

                report.setText(reportText);
                report.setStatus(ReportStatus.FINISHED);
                reportRepository.save(report);

            } catch (Exception e) {
                report.setText("Error during report text's generation: " + e.getMessage());
                report.setStatus(ReportStatus.ERROR);
            }
        });
    }

    private Report getById(Long id){
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    private String reportText(long userCount, long userTime,
                              long topicTime, List<Topic> topics,
                              long totalTime){
        StringBuilder text = new StringBuilder("<tr><td>Id</td><td>Name</td><td>Created at</td><td>Description</td></tr>");
        for (Topic t: topics) {
            text.append(String.format(
                    "<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                    t.getId(), t.getName(), t.getCreatedAt(), t.getDescription()
            ));
        }
        return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Report</title>
                </head>
                <body>
                    Total time: %d
                    <br>
                    <div>
                        <h1>Users</h1>
                        Count: %d <br>
                        Time: %d <br>
                    </div>
                    <div>
                        <h1>Topics</h1>
                        Time: %d <br>
                        <table>
                        %s
                        </table>
                    </div>               
                </body>
                </html>
                """,
                totalTime, userCount, userTime, topicTime, text);
    }
}
