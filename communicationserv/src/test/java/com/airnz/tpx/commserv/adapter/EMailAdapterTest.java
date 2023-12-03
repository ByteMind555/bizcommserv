package com.airnz.tpx.commserv.adapter;

import com.airnz.tpx.commserv.data.EmailRepository;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.testutils.TestUtil;
import generated.MessageRequest;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EMailAdapterTest {

    @Mock
    EmailRepository emailBoxRepository;
    @InjectMocks
    EMailAdapter EMailAdapter;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPushMessageSingleMailSendSuccess() {
        MessageRequest messageRequest = TestUtil.getMessageRequest
                ("diwakar.david@gmail.com", "testUser1@test.com",
                        "testUser2@test.com", "Test Mail Subject", "TEXT",
                        "Merry Christmas to everybody. Ho Ho Ho");
        MessageResponse messageResponse = EMailAdapter.pushMessage(messageRequest);
        Assert.assertNotNull(messageResponse.getId());
        Assert.assertEquals(messageResponse.getContent().getMessageFrom().getId(),
                "diwakar.david@gmail.com");
        Assert.assertEquals(messageResponse.getContent().getMessageBody().getSubject(),
                "Test Mail Subject");
        Assert.assertEquals(messageResponse.getContent().getMessageBody().getContents().get(0).getContentDetails(),
                "Merry Christmas to everybody. Ho Ho Ho");

    }

    @Test
    public void testPushMessageMultiMailSendSuccess() {
        Mockito.when(emailBoxRepository.getMessages()).thenCallRealMethod();

        MessageRequest messageRequest1 = TestUtil.getMessageRequest
                ("diwakar.david@gmail.com", "testUser1@test.com",
                        "testUser2@test.com", "Test Mail Subject",
                        "TEXT","Merry Christmas to everybody. Ho Ho Ho");

        MessageRequest messageRequest2 = TestUtil.getMessageRequest
                ("testUser1@test.com", "diwakar.david@gmail.com",
                        "testUser2@test.com", "Test Mail Subject",
                        "TEXT","Thanks for your wishes buddy :)");


        MessageResponse messageResponse1 = EMailAdapter.pushMessage(messageRequest1);
        Assert.assertNotNull(messageResponse1.getId());
        Assert.assertEquals(messageResponse1.getContent().getMessageFrom().getId(),
                "diwakar.david@gmail.com");
        Assert.assertEquals(messageResponse1.getContent().getMessageBody().getSubject(),
                "Test Mail Subject");
        Assert.assertEquals(messageResponse1.getContent().getMessageBody().getContents().get(0).getContentDetails(),
                "Merry Christmas to everybody. Ho Ho Ho");

        MessageResponse messageResponse2 = EMailAdapter.pushMessage(messageRequest2);
        Assert.assertNotNull(messageResponse2.getId());
        Assert.assertEquals(messageResponse2.getContent().getMessageFrom().getId(),
                "testUser1@test.com");
        Assert.assertEquals(messageResponse2.getContent().getMessageBody().getSubject(),
                "Test Mail Subject");
        Assert.assertEquals(messageResponse2.getContent().getMessageBody().getContents().get(0).getContentDetails(),
                "Thanks for your wishes buddy :)");
    }

    @Test
    public void testGetMessages() {
        Mockito.when(emailBoxRepository.getMessages()).thenCallRealMethod();

        MessageRequest messageRequest1 = TestUtil.getMessageRequest
                ("diwakar.david@gmail.com", "testUser1@test.com",
                        "testUser2@test.com", "Test Mail Subject",
                        "TEXT","Merry Christmas to everybody. Ho Ho Ho");

        MessageRequest messageRequest2 = TestUtil.getMessageRequest
                ("testUser1@test.com", "diwakar.david@gmail.com",
                        "testUser2@test.com", "Test Mail Subject",
                        "TEXT","Thanks for your wishes buddy :)");

        MessageResponse messageResponse1 = EMailAdapter.pushMessage(messageRequest1);
        MessageResponse messageResponse2 = EMailAdapter.pushMessage(messageRequest2);
        EmailSearchCriteria emailSearchCriteria = new EmailSearchCriteria("token testUser2@test.com",
                "INBOX", "", "", "","");
        MessageSearchResponse messages = EMailAdapter.getMessages(emailSearchCriteria);
        Assert.assertNotNull(messages);
        // Since 'testUser2@test.com' has not sent any mails but a recipient for mails from both
        // diwakar.david@gmail.com and  testUser1@test.com
        Assert.assertEquals(messages.getMessages().size(), 2);
    }
}
