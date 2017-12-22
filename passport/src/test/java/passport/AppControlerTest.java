package passport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cn.ce.passport.Application;
import cn.ce.passport.dao.persistence.User;

/**
 * Created by fu on
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AppControlerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	// @Test
	// public void checkuserName() throws Exception {
	// MvcResult result = mockMvc
	// .perform(post("/checkUserName").param("userName", "111"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void checkEmail() throws Exception {
	// MvcResult result = mockMvc
	// .perform(post("/checkEmail").param("email", "fuqingyan@300.cn"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

//	 @Test
//	 public void sendEmail() throws Exception {
//	 MvcResult result = mockMvc
//	 .perform(post("/sendEmail").param("email", "fuqingyan@300.cn"))
//	 .andExpect(status().isOk()).andReturn();
//	 String json = result.getResponse().getContentAsString();
//	 System.out.println(json);
//	 }

//	 @Test
//	 public void testLog() throws Exception {
//	 MvcResult result = mockMvc
//	 .perform(
//	 post("/login").param("userName", "fuqy").param(
//	 "password", "e10adc3949ba59abbe56e057f20f883e").param("sysId", "103"))
//	 .andExpect(status().isOk()).andReturn();
//	 String json = result.getResponse().getContentAsString();
//	 System.out.println(json);
//	 }

//	@Test
//	public void register() throws Exception {
//
//		User user = new User();
//		MvcResult result = mockMvc
//				.perform(post("/register")
//								.param("code", "189209").param("sysId", "103").param("userInfo", "a"))
//				.andExpect(status().isOk()).andReturn();
//		String json = result.getResponse().getContentAsString();
//		System.out.println(json);
//	}

	// @Test
	// public void getUserInfobyUid() throws Exception {
	// MvcResult result = mockMvc.perform(post("/getUserInfobyUid")
	// .param("uid", "100099"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	
	//yRMtzWbxdpZFiSf7P5v0P78e3Ykz4LGZSatYsT2VXOI=
	//BCQ/fjvYUV6hcc0ysKbFkbeJeRXSOuswsvEd6w6y2gk=
	 @Test
	 public void testticket() throws Exception {
	 MvcResult result = mockMvc.perform(post("/checkTicket")
	 .param("ticket", "yRMtzWbxdpZFiSf7P5v0P78e3Ykz4LGZSatYsT2VXOI="))
	 .andExpect(status().isOk())
	 .andReturn();
	 String json = result.getResponse().getContentAsString();
	 System.out.println(json);
	 }

//	 @Test
//	 public void testticket() throws Exception {
//	 MvcResult result = mockMvc
//	 .perform(post("/logout").param("userId", "100098"))
//	 .andExpect(status().isOk()).andReturn();
//	 String json = result.getResponse().getContentAsString();
//	 System.out.println(json);
//	 }

//	 @Test
//	 public void testupdate() throws Exception {
//	 MvcResult result = mockMvc
//	 .perform(
//	 post("/updateUser").param("userInfo","{'idCard': '03800138000','enterpriseName': 'ce','uid': '30ed82b6f5', 'email': 'fuqingyan12@300.cn'}"))
//	 .andExpect(status().isOk()).andReturn();
//	 String json = result.getResponse().getContentAsString();
//	 System.out.println(json);
//	 }

//	 @Test
//	 public void sendmail() throws Exception {
//	 MvcResult result = mockMvc
//	 .perform(
//	 post("/sendMailforPassword").param("email",
//	 "fuqingyan@300.cn")).andExpect(status().isOk())
//	 .andReturn();
//	 String json = result.getResponse().getContentAsString();
//	 System.out.println(json);
//	 }

	// @Test
	// public void resetpw() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/resetPassword").param("code",
	// "VcDUOchbp3YO0VVwFsh+zYFnQhCc1afxbpIZcHHfLrY=").param(
	// "password", "fuqingyan"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void changePassword() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/changePassword")
	// .param("oldPassword",
	// "73882AB1FA529D7273DA0DB6B49CC4F3")
	// .param("userId", "100099")
	// .param("newPassword", "444444"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void modUserState() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/modUserState")
	// .param("userId", "8").param("state", "0"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void testticket() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/auditUser").param("userIds", "1,8,9")
	// .param("checkMem", "100098")
	// .param("checkState", "2"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void getUserList() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/getUserList").param("currentPage", "1").param("pageSize", "10"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

}
