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

/**
 * Created by fu .
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthControlerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	// @Test
	// public void testAddauth() throws Exception {
	// MvcResult result = mockMvc.perform(post("/auth/addAuth")
	// .param("authName", "niw新权限")
	// .param("systemId", "2"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }
	//
	// @Test
	// public void updateauth() throws Exception {
	// MvcResult result = mockMvc.perform(post("/auth/updateAuth")
	// .param("authId", "108")
	// .param("authName", "打ajkl人")
	// .param("url", "www.amaaaa.com")
	// .param("systemId", "4"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void testgetAuth() throws Exception {
	// MvcResult result = mockMvc.perform(post("/getAuth")
	// .param("authId", "1"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }
	// @Test
	// public void getauths() throws Exception {
	// MvcResult result = mockMvc.perform(post("/auth/getAuths")
	// .param("authName", "打人")
	// .param("systemId","2").param("isvalid", "2"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

	// @Test
	// public void getauths() throws Exception {
	// MvcResult result =
	// mockMvc.perform(post("/auth/getAllAuths").param("roleId", "99"))
	// .andExpect(status().isOk())
	// .andReturn();
	// String json = result.getResponse().getContentAsString();
	// System.out.println(json);
	// }

}
