# =========================================================================
# SCRIPT DE COMPILAÇÃO E PUBLICAÇÃO DOCKER NO AWS ECR
# =========================================================================

$AWS_ACCOUNT_ID = "530923037363"
$AWS_ACCESS_KEY = $env:AWS_ACCESS_KEY_ID
$AWS_SECRET_KEY = $env:AWS_SECRET_ACCESS_KEY
$AWS_REGION = "us-east-1"

$REPOSITORY_NAME = "aliancapsicossocial-api"
$REGISTRY_URL = "$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
$IMAGE_TAG = "latest"

Write-Host "=================================================================" -ForegroundColor Cyan
Write-Host " Iniciando Deploy no AWS ECR" -ForegroundColor Cyan
Write-Host "=================================================================" -ForegroundColor Cyan

# 1. Compilar aplicação
Write-Host "[1/4] Compilando aplicação..." -ForegroundColor Yellow

./mvnw compile

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha ao compilar a aplicação."
    exit $LASTEXITCODE
}

# 2. Obter token ECR
Write-Host "[2/4] Obtendo token temporário do ECR..." -ForegroundColor Yellow

$EcrPassword = ./mvnw exec:java `
    -q `
    -Dexec.mainClass="br.com.aliancapsicossocial.helpers.EcrLoginHelper" `
    -Dexec.args="$AWS_ACCESS_KEY $AWS_SECRET_KEY $AWS_REGION"

if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($EcrPassword)) {
    Write-Error "Falha ao obter token do AWS ECR."
    exit 1
}

$EcrPassword = $EcrPassword.Trim()

# 3. Login Docker
Write-Host "[3/4] Autenticando Docker no ECR..." -ForegroundColor Yellow

Write-Output $EcrPassword | docker login `
    --username AWS `
    --password-stdin `
    $REGISTRY_URL

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha no login do Docker."
    exit $LASTEXITCODE
}

# 4. Build e Push
Write-Host "[4/4] Docker Build e Push..." -ForegroundColor Yellow

Write-Host "Compilando imagem..." -ForegroundColor Gray

docker build -t $REPOSITORY_NAME .

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha no Docker Build."
    exit $LASTEXITCODE
}

Write-Host "Aplicando tag..." -ForegroundColor Gray

docker tag `
    "$($REPOSITORY_NAME):latest" `
    "$($REGISTRY_URL)/$($REPOSITORY_NAME):$($IMAGE_TAG)"

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha ao aplicar tag."
    exit $LASTEXITCODE
}

Write-Host "Enviando imagem para o ECR..." -ForegroundColor Gray

docker push `
    "$($REGISTRY_URL)/$($REPOSITORY_NAME):$($IMAGE_TAG)"

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha ao enviar imagem para o ECR."
    exit $LASTEXITCODE
}

Write-Host "=================================================================" -ForegroundColor Green
Write-Host " DEPLOY CONCLUÍDO COM SUCESSO!" -ForegroundColor Green
Write-Host (" Imagem disponível em: {0}/{1}:{2}" -f $REGISTRY_URL, $REPOSITORY_NAME, $IMAGE_TAG) -ForegroundColor Green
Write-Host "=================================================================" -ForegroundColor Green