# Variáveis
IMAGE_NAME ?= problem1-app
TAG ?= latest
PORT ?= 8080
JAR ?= problem1-0.0.1-SNAPSHOT.jar

.PHONY: docker-build docker-run docker-stop docker-logs

# Builda a imagem usando o Dockerfile
docker-build:
	docker build --build-arg JAR=$(JAR) -t $(IMAGE_NAME):$(TAG) .

# Sobe o container expondo a porta (ajuste PORT se precisar)
docker-run:
	docker run --rm -d --name $(IMAGE_NAME) -p $(PORT):8080 $(IMAGE_NAME):$(TAG)

# Para o container
docker-stop:
	- docker stop $(IMAGE_NAME)

# Logs do container
docker-logs:
	docker logs -f $(IMAGE_NAME)

