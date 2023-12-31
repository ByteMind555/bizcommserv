# 🚀 bizcommserv(Business Communication Service) : Elevating Your Communication Experience! 🌐💬

Welcome aboard Air New Zealand's groundbreaking Technology Platform eXperience (TPX)! Our bizcommserv is your passport to seamless communication within Air New Zealand, boasting RESTful services for eMail, SMS, and Chats (soon, for both user to user & user to group). 🚀✉️🤝

## Assumptions
1. We're bringing three cool functionalities to the stage:
   1. **Send Message**
   2. **Get Message with Search capability**
   3. **Save as Draft**
2. Not using Spring for any Authorization or Authentication even though we are using the Authorization header. But the lookups will happen only with the Id the request is made. Hence risk of fetching  other users message is still not a threat for this MVP.
3. For our MVP, will be going with in-memory Java collections for storing mails, Hence no databases and mailboxes ! It's a demo that's as dynamic as it gets!
4. The FT verification was manually done with Postman. The following is the postman collection used invoking the service.
   https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/AirNZ_Email_Collection.postman_collection.json
   FT Code:
   https://github.com/ByteMind555/bizcommserv/tree/main/communicationservfunctionaltest/src/test/java/com/airnz/tpx/communicationserv/ft
5. For the automation of the deployment I am new to AWS and hence have started to understand CodeDeploy [at](https://aws.amazon.com/codedeploy/ )  
       
## Design Considerations
1. We're thinking big with a 'Contract First' API Design approach. Created the OAS Spec 2.0:
2. Dive into the swagger specification at [GitHub Swagger Spec](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/main/resources/schema/swagger.json).
3. Copy the swagger.json content, paste it in [Swagger Editor](https://editor.swagger.io/), and witness the magic unfold.
4. From swagger.json, have conjured up the Java spec file powering the entire show.
![Swagger](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/SwaggerPayload.PNG)

5. The high level design for solution as follows  
![Design](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/drawio.png)

## Output for verification
1. **Server Up and running**
   
![Server Up](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/serverUp.PNG)

Hence we are good to start the verification steps
      
2. **Sending the Message**

Step 1:
First Message sent by diwakar.david@gmail.com to testUser1@gmail.com and testUser2@gmail.com

PostMan:

![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail1_Swagger.PNG)

Logs:

![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail1_logs.PNG)

In the attached Logs you could see 3 Users and their Mail Boxes with 1 message each. 

Since diwakar.david@gmail.com sent the mail will be in the 'SENT' mailbox location
and testUser1@gmail.com and testUser2@gmail.com will have the mail in the 'INBOX' mailbox location.


| MailId  | INBOX | SENT| DRAFT |OUTBOX | 
| ------------- | ------------- | ------------- | ------------- |------------- |
| diwakar.david@gmail.com  |   |  1 | ||
| testUser1@gmail.com  | 1 | | | |
| testUser2@gmail.com  | 1 | | | |


Step 2:
Second Message sent by testUser1@gmail.com to diwakar.david@gmail.com and testUser2@gmail.com

PostMan:

![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail2_testUser_postman.PNG)

Logs:

![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/sendEmail2_testUser_logs.PNG)

In the attached Logs you could see 3 Users and their Mail Boxes with 2 messages each. 

Since testUser1@gmail.com sent the mail it will in 'SENT' mailbox location. 
and testUser1@gmail.com and testUser2@gmail.com will have the mail in the 'INBOX' mailbox location
Hence:

As per the screenshot above
diwakar.david@gmail.com will contain 1 message in 'SENT' and 1 message in 'INBOX'
testUser1@gmail.com will contain 1 message in 'INBOX' and 1 message in 'SENT'
testUser2@gmail.com since he has not sent any messages he will have 2 messages in his 'INBOX' which is displayed in the screenshot.

| MailId  | INBOX | SENT| DRAFT |OUTBOX | 
| ------------- | ------------- | ------------- | ------------- |------------- |
| diwakar.david@gmail.com  |1   |  1 | ||
| testUser1@gmail.com  | 1 | 1| | |
| testUser2@gmail.com  | 2 | | | |

3. **Get Message from Inbox**
PostMan: Since diwakar just received one mail there is only 1 mail in his 'INBOX'. 
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/getEmails_from_mailbox_postman.PNG)

The request has fectched the message from diwakar.david@gmail.com 'INBOX' mailbox
Since there is just 1 message the respective message can be found  

3. **Save as Draft**
PostMan:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/saveDraft_Diwakar_postman.PNG)
Logs:
![Send Email](https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/test/java/resource/saveDraft_Diwakar_log.PNG)

Difference between Draft and Send is the message is stored in the 'DRAFT' mailBox. There is no mail to the recipient. 
So that is visible the attched logs

| MailId  | INBOX | SENT| DRAFT |OUTBOX | 
| ------------- | ------------- | ------------- | ------------- |------------- |
| diwakar.david@gmail.com  |   |   | 1||
| testUser1@gmail.com  |  | | | |
| testUser2@gmail.com  |  | | | |


## Callouts
0. SONARCube, DAST & SAST (Dynamic and Static security vulnerabilities), findbugs, PMD, CPD were not configured yet. Need to setup Jenkins - For MVP we are skipping thoses for now.  
1. Facing OpenAPI codegen issues during generation of Java Classes with Gradle. Hence switched to Maven to quickly get solution delivered for review, will work to get this convered to Gradle parallely. 
2. Though Search API(GET http://localhost:8080/v1/mails/) supports pagination, sorting results and other features, they are not implemented for the demo. 
3. The REST POST supports 'async' header. This is used to do long running opertaion. The request takes a boolen value heaader. Not implemented for this demo. Need to expose a getStatus API to get the status.
4. Performance test was not carried out for the solution.
5. Verification was done locally by running in a local tomcat and Postman for invoking the request. FT location https://github.com/ByteMind555/bizcommserv/tree/main/communicationservfunctionaltest/src/test/java/com/airnz/tpx/communicationserv/ft

Get ready to embark on a communication revolution within Air New Zealand! Please reachout to me Diwakar <diwakar.david@gmail.com>
