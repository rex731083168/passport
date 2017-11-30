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
 * Created by fu
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class RoleControlerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

//	@Test
//	public void getauths() throws Exception {
//		MvcResult result = mockMvc
//				.perform(
//						post("/role/allotAuth").param("roleId", "3").param(
//								"auths", "115,116,117,118,119,120,121,122"))
//				.andExpect(status().isOk()).andReturn();
//		String json = result.getResponse().getContentAsString();
//		System.out.println(json);
//	}

}
