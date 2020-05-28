package com.central.config.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.common.net.HttpHeaders;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.ws.rs.Path;

@Path("/documentacao")
@Configuration
@EnableSwagger2
public class SwaggerConfiguration   {
	
	/**
	 * http://localhost:8080/swagger-ui.html#/ http://localhost:8080/v2/api-docs
	 */
	
	public static final Contact DEFAULT_CONTACT = new Contact("squad 1", "https://github.com/codenation-dev/squad-1-ad-java-contaazul-1", 
			"Larissa, Nathalia, Paula, Silmara, Vanessa");
	
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		  .select()                                  
		  .apis(RequestHandlerSelectors.basePackage("com.central"))    
		  .paths(PathSelectors.any())
		  .build()
		  .useDefaultResponseMessages(false)                                   
		  .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
		  .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		  .securityContexts(Arrays.asList(securityContext()))
		  .apiInfo(apiInfo());
 }

		private ApiInfo apiInfo() {
			return new ApiInfoBuilder()
					.title("LADY BUG - Central de Erros REST API")
					.description("\"Rest API\"")
					.contact(new Contact("Squad 1 ", "https://github.com/codenation-dev/squad-1-ad-java-contaazul-1", "Larissa, Nathalia, Paula, Silmara, Vanessa"))
					.build();
}

 private List<ResponseMessage> responseMessageForGET()
 {
     return new ArrayList<ResponseMessage>() {
         private static final long serialVersionUID = 1L;

         {
         add(new ResponseMessageBuilder()   
             .code(500)
             .message("500 message")
             .responseModel(new ModelRef("Error"))
             .build());
         add(new ResponseMessageBuilder() 
             .code(403)
             .message("Forbidden!")
             .build());
     }};
 }

 private SecurityContext securityContext() {
     return SecurityContext.builder()
         .securityReferences(defaultAuth())
         .forPaths(PathSelectors.ant("/user/**"))
         .build();
 }
 
 List<SecurityReference> defaultAuth() {
     AuthorizationScope authorizationScope
         = new AuthorizationScope("ADMIN", "accessEverything");
     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
     authorizationScopes[0] = authorizationScope;
     return Arrays.asList(
         new SecurityReference("Token Access", authorizationScopes));
 }
 
 
    }


		
	
	

