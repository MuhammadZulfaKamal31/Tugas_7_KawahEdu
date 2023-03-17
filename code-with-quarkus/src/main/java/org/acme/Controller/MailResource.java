package org.acme.Controller;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/kirim")
@Singleton
public class MailResource {

        @Inject
        Mailer mailer;
        @Inject
        ReactiveMailer reactiveMailer;

        @GET
        @Scheduled(cron = "0 0 0 L * ? ") // L artinya itu akhir bulan
        public Uni<Void> sendEmailWithAttachment() {
                return reactiveMailer.send(Mail.withText("zulfakamal019@gmail.com",
                                "Laporan harian",
                                "Pencatatan hasil kebun Pak Dengklek.")
                                .addAttachment("MyFirstExcel.xlsx", new File("MyFirstExcel.xlsx"),
                                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        }
}
