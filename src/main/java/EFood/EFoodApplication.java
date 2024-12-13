package EFood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication(scanBasePackages = "EFood")
public class EFoodApplication {

	public static void main(String[] args) {
		// Load the .env file
		// Dotenv dotenv = Dotenv.load();

		// Retrieve credentials from the .env file for hosting
		String dbUrl = System.getenv("DB_URL");
		String dbUsername = System.getenv("DB_USERNAME");
		String dbPassword = System.getenv("DB_PASSWORD");
		String jwt_secret_key = System.getenv("JWT_SECRET_KEY");
		String cloud_name = System.getenv("CLOUD_NAME");
		String cloud_api_key = System.getenv("CLOUD_API_KEY");
		String cloud_api_secret = System.getenv("CLOUD_API_SECRET");

		// // // for checking in local host
		// String dbUrl = dotenv.get("DB_URL");
		// String dbUsername = dotenv.get("DB_USERNAME");
		// String dbPassword = dotenv.get("DB_PASSWORD");
		// String jwt_secret_key = dotenv.get("JWT_SECRET_KEY");
		// String cloud_name = dotenv.get("CLOUD_NAME");
		// String cloud_api_key = dotenv.get("CLOUD_API_KEY");
		// String cloud_api_secret = dotenv.get("CLOUD_API_SECRET");

		// Pass them to Spring Boot's datasource configuration
		System.setProperty("datasource.url", dbUrl);
		System.setProperty("datasource.username", dbUsername);
		System.setProperty("datasource.password", dbPassword);
		System.setProperty("cloud.api.secret", cloud_api_secret);
		System.setProperty("cloud.api.key", cloud_api_key);
		System.setProperty("cloud.name", cloud_name);
		System.setProperty("jwt.secret.key", jwt_secret_key);
		SpringApplication.run(EFoodApplication.class, args);
	}

}
