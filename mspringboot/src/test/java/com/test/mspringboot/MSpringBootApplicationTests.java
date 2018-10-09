package com.test.mspringboot;

import com.test.mspringboot.property.BlogProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MSpringBootApplication.class)
public class MSpringBootApplicationTests {

	@Autowired
	private BlogProperties blogProperties;
	@Test
	public void contextLoads() {
		System.out.println(blogProperties.getName());
	}

}
