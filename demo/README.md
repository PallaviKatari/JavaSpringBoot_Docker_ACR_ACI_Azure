# Step 1: Create Resource Group
az group create --name rg-kataripallavi-0973 --location eastus

# Step 2: Create Azure Container Registry (ACR)
az acr create --resource-group rg-kataripallavi-0973 --name webinarjavaacr --sku Basic --location eastus

# Step 3: Enable Admin User for ACR
az acr update -n webinarjavaacr --admin-enabled true

# Step 4: Retrieve ACR Credentials
az acr credential show --name webinarjavaacr

# Step 5: Docker Login to ACR
docker login webinarjavaacr.azurecr.io -u webinarjavaacr -p <PASSWORD>
# Replace <PASSWORD> with the value from az acr credential show

# Step 6: Build Docker Image (local)
docker build -t demo:latest .

# Step 7: Run Docker Image locally (test before pushing)
docker run -p 8080:8080 demo:latest

# Step 8: Tag Docker Image for ACR
docker tag demo:latest webinarjavaacr.azurecr.io/demo:latest

# Step 9: Push Docker Image to ACR
docker push webinarjavaacr.azurecr.io/demo:latest

# Step 10: Create Azure Container Instance (ACI)
az container create \
  --resource-group rg-kataripallavi-0973 \
  --name demo-container \
  --image webinarjavaacr.azurecr.io/demo:latest \
  --registry-login-server webinarjavaacr.azurecr.io \
  --registry-username webinarjavaacr \
  --registry-password <PASSWORD> \
  --ports 8080 \
  --ip-address Public \
  --os-type Linux \
  --cpu 1 \
  --memory 1.5

# Step 11: Get Public IP of Container
az container show --resource-group rg-kataripallavi-0973 --name demo-container --query ipAddress.ip --output tsv
