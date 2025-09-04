import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/s3")
public class S3PipelineController {

    @PostMapping("/bucket")
    public ResponseEntity<String> triggerPipeline(@RequestParam String bucketName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            // Use variável de ambiente para o token, nunca hardcode!
            String githubToken = System.getenv("GITHUB_TOKEN");
            if (githubToken == null || githubToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token de autenticação do GitHub não está configurado.");
            }

            headers.setBearerAuth(githubToken);
            headers.set("Accept", "application/vnd.github+json");
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-GitHub-Api-Version", "2022-11-28");

            String url = "https://api.github.com/repos/lautongl/tw-project/actions/workflows/terraform-simple.yml/dispatches";
            String body = String.format(
                "{\"ref\":\"main\",\"inputs\":{\"bucket_name\":\"%s\"}}",
                bucketName
            );

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            // Status 204 indica sucesso sem conteúdo
            if (response.getStatusCodeValue() == 204) {
                return ResponseEntity.ok("Pipeline disparada para bucket " + bucketName);
            } else {
                String respBody = response.getBody();
                String msg = respBody != null 
                    ? "Falha ao disparar pipeline: " + respBody 
                    : "Falha ao disparar pipeline: status " + response.getStatusCodeValue();
                return ResponseEntity.status(response.getStatusCode()).body(msg);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Erro ao disparar pipeline: " + e.getMessage());
        }
    }
}
