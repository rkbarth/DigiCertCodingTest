package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RestController  // <--- MUST HAVE THIS
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// 1. Handles the root and /hello (default)
	@GetMapping({"/", "/hello"})
	public String defaultHello() {
		return "<h1>Hello, World!</h1><p>Try adding a name to the URL, like <code>/hello/Gemini</code></p>";
	}

	// 2. Handles /hello/ANYTHING
	@GetMapping("/hello/{name}")
	public String sayHelloToPerson(@PathVariable("name") String name) {
		if (name.equals(name.toUpperCase())) {
			return "<h1>STOP YELLING, " + name + "!</h1>";
		}
		return "<h1>Hello, " + name + "!</h1>";gi
	}

}
