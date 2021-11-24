Reset Password Module
=====================

### Structure
1. Input Email 
3. Send token 
4. Access to change page
5. Update password


### Details
1. Input Email

* Get input email and send email

    MimeMessage mail = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
    helper.setFrom("Sender Email");
    helper.setTo("Receiver email");
    helper.setSubject("Mail Subject");
    helper.setText("Mail Contents", true); //true for HTML

*
