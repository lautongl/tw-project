variable "aws_region" {
  description = "AWS region"
  type        = string
}

variable "bucket_name" {
  description = "Nome do bucket"
  type        = string
}

variable "environment" {
  description = "Nome do ambiente (ex: dev, prod)"
  type        = string
}