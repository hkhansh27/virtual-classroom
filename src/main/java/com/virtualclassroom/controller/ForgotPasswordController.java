package com.virtualclassroom.controller;

import com.virtualclassroom.model.User;
import com.virtualclassroom.service.user.UserNotFoundException;
import com.virtualclassroom.service.user.UserService;
import com.virtualclassroom.utils.Helper;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    private final UserService userService;
    private final JavaMailSender mailSender;

    public ForgotPasswordController(UserService userService, JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            userService.updateResetPasswordToken(token, email);
            //generate reset password link
            String resetPasswordLink = Helper.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPasswordLink);

            //send email
            sendEmail(email, resetPasswordLink);

            //notify
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email.");
        }

        return "forgot_password_form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@class.com", "Classroom Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password: </p>"
                    + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a><b></p>"
                    + "<p>Ignore this email if you remember your password, or you have not made this request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title","Reset your password");
            model.addAttribute("message","Invalid Token");
            return "message";
        }

        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset Your Password");
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String proecessResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title","Reset your password");
            model.addAttribute("message","Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "message";
    }
}
