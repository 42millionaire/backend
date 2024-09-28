# # Makefile

# # 변수 설정
# APP_NAME=42millionaire
# DOCKER_IMAGE_NAME=42millionaire
# DOCKER_COMPOSE_FILE=docker-compose.yml
# POSTGRES_MOUNT_DB=~/postgres-data

# # .PHONY는 특정 작업이 파일 이름과 겹치더라도 충돌을 피하기 위해 사용
# .PHONY: build run clean docker-build docker-up


# ALL: docker-up
# # Spring Boot 빌드 (JAR 파일 생성)
# build:
# 	mkdir -p $(POSTGRES_MOUNT_DB)

# # Docker 이미지 빌드 (Spring Boot 빌드 후 실행)
# docker-build: build
# 	docker build -t $(DOCKER_IMAGE_NAME) .

# # Docker Compose로 컨테이너 실행
# docker-up: docker-build
# 	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d
# 	docker rmi 42millionaire

# # 컨테이너 종료 및 정리
# clean:
# 	docker-compose -f $(DOCKER_COMPOSE_FILE) down --rmi "all" --volumes
# 	docker system prune -f


# Makefile

# 변수 설정
APP_NAME=42millionaire
DOCKER_IMAGE_NAME=42millionaire
DOCKER_COMPOSE_FILE=docker-compose.yml
POSTGRES_MOUNT_DB=~/postgres-data
DOCKERFILE_PATH=42millionaire_spring/Dockerfile

# .PHONY는 특정 작업이 파일 이름과 겹치더라도 충돌을 피하기 위해 사용
.PHONY: build run clean docker-build docker-up

# 모든 작업을 수행하는 기본 타겟
ALL: docker-up

# PostgreSQL 데이터 볼륨 디렉터리 생성
build:
	mkdir -p $(POSTGRES_MOUNT_DB)

# Docker 이미지 빌드 (Dockerfile 경로 명시)
docker-build: build
	docker build -f $(DOCKERFILE_PATH) -t $(DOCKER_IMAGE_NAME) ./42millionaire_spring

# Docker Compose로 컨테이너 실행
docker-up: docker-build
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

# 컨테이너 종료 및 정리
clean:
	docker-compose -f $(DOCKER_COMPOSE_FILE) down --rmi "all" --volumes
	docker system prune -f
