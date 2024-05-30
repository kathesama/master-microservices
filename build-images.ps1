# Lista de todos los microservicios
$services = "account-service",
            "loan-service",
            "card-service",
            "infrastructure/configuration-server",
            "eureka-server",
            "gateway-server",
            "rabbitmq-broker";

# Itera sobre cada servicio
foreach ($service in $services)
{
    Write-Host "Building Docker image for $service"
    # Navega al directorio del servicio
    Set-Location -Path $service
    # Ejecuta el comando Maven
    mvn compile jib:dockerBuild
    # Vuelve al directorio raíz
    Set-Location -Path ..
}

Set-Location -Path ..