# bizcommserv
This is an Air New Zealand - Technology Platform eXperience(TPX) offering. The platform exposes REST service for communication, supporting channels like eMail, SMS and Chats(user to user &amp; user to group) in the future.

# Assumptions
1. Not using DB or Mailbox. For the MVP using in-memory volatile java collection for the Demo.
2. Have implemented 3 funcationalities
   1. Send Message
   2. Improvised the Get Message and using it as a search
   3. Save as Draft   



# Design Considerations
1. Have taken a Contract First API Design approach and taking a "thinking big" approach.
2. Prepared swagger speicifation which will be available in the following location.
```
https://github.com/ByteMind555/bizcommserv/blob/main/communicationserv/src/main/resources/schema/swagger.json
```     
4. Please copy json contents of swagger.json and paste it in the below URL, Select calce if it wants to convert the value to json.
```
https://editor.swagger.io/
```
5. From swagger.json generated the java spec file which is used in the applciation.
