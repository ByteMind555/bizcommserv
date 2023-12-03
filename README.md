# 🚀 bizcommserv: Elevating Your Communication Experience! 🌐💬

Welcome aboard Air New Zealand's groundbreaking Technology Platform eXperience (TPX)! Our bizcommserv is your passport to seamless communication, boasting RESTful services for eMail, SMS, and Chats (soon, for both user to user & user to group). 🚀✉️🤝

## Output for verification
1. **Server Up and running**
![Server Up](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/serverUp.PNG)
Hence we are good to start the test
      
2. **Sending the Message**
First Message sent by diwakar.david"gmail.com to testUser1.gmail.com and testUser2@gmail.com
PostMan:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail1_Swagger.PNG)
Logs:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail1_logs.PNG)

Second Message sent by testUser1.gmail.com to diwakar.david"gmail.com and testUser2@gmail.com
PostMan:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail2_testUser_postman.PNG)
Logs:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail2_testUser_logs.PNG)

3. **Get Message from Inbox**
PostMan: Since diwakar just received one mail there is only 1 mail in his 'INBOX'. 
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/getEmails_from_mailbox_postman.PNG)

4. **Save as Draft**
PostMan:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/saveDraft_Diwakar_postman.PNG)
Logs:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/saveDraft_Diwakar_log.PNG)


## Assumptions
1. Forget databases and mailboxes! For our MVP, we're wowing you with in-memory volatile Java collections. It's a demo that's as dynamic as it gets!
2. We're bringing three cool functionalities to the stage:
   1. **Send Message**
   2. **Next-Level Get Message with Search Magic**
   3. **Save as Draft**


## Design Considerations
1. We're thinking big with a 'Contract First' API Design approach.
2. Dive into the swagger specification at [GitHub Swagger Spec](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/main/resources/schema/swagger.json).
3. Copy the swagger.json content, paste it in [Swagger Editor](https://editor.swagger.io/), and witness the magic unfold.
4. From swagger.json, have conjured up the Java spec file powering the entire show.
![Swagger](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/SwaggerPayload.PNG)

5. The high level design for solution as follows  
![Design](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/drawio.png)

## Callouts
1. Was having Java class generation issue with Gradle. Hence switched to Maven as I am more comfortable to get solution delivered, will work to get this convered to Gradle. 
2. Though the Search API supports pagination,sorting and other features they have not been implemented for the demo.  


Get ready to embark on a communication revolution! 💬✨ #BizCommServ
