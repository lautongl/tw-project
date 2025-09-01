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
            headers.setBearerAuth("SEU_GITHUB_TOKEN");
            headers.set("Accept", "application/vnd.github+json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = "https://api.github.com/repos/SEU_OWNER/SEU_REPO/actions/workflows/terraform-simple.yml/dispatches";
            String body = String.format(
                "{\"ref\":\"main\",\"inputs\":{\"bucket_name\":\"%s\"}}",
                bucketName
            );

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("Pipeline disparada para bucket " + bucketName);
            } else {
                return ResponseEntity.status(response.getStatusCode())
                       .body("Falha ao disparar pipeline: " + response.getBody());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Erro ao disparar pipeline: " + e.getMessage());
        }
    }
}
