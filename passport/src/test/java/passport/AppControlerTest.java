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
	// public void checkUsername() throws Exception {
	// MvcResult result = mockMvc
	// .perform(post("/checkUsername").param("username", "fuqy"))
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

	// @Test
	// public void sendEmail() throws Exception {
	// MvcResult result = mockMvc
	// .perform(post("/sendEmail").param("email", "fuqingyan@300.cn"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

//	@Test
//	public void testLog() throws Exception {
//		MvcResult result = mockMvc
//				.perform(
//						post("/login").param("username", "fuqy").param(
//								"password", "123456"))
//				.andExpect(status().isOk()).andReturn();
//		String json = result.getResponse().getContentAsString();
//		System.out.println(json);
//	}

	// @Test
	// public void getRoleIdsByUid() throws Exception {
	//
	// MvcResult result = mockMvc
	// .perform(
	// post("/register")
	// .param("username", "test1")
	// .param("email", "heihei.com")
	// .param("password",
	// "E10ADC3949BA59ABBE56E057F20F883E"))
	// .andExpect(status().isOk()).andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void getUserInfobyUid() throws Exception {
	// MvcResult result = mockMvc.perform(post("/getUserInfobyUid")
	// .param("uid", "100099"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	//
	// @Test
	// public void testticket() throws Exception {
	// MvcResult result = mockMvc.perform(post("/checkTicket")
	// .param("ticket", "sKapEpa/cq8FcMLORGXLnw=="))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void testupdate() throws Exception {
	// MvcResult result = mockMvc
	// .perform(
	// post("/updateUser").param("userId", "100099")
	// .param("username", "woqu")
	// .param("email", "test@135.cn"))
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

}
