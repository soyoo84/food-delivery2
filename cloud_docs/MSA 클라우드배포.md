Ŭ���� ȯ�� �������� �޾ƿ� �ֿ� ������ ����ؼ� MSA �� ���� �� �����Դϴ�.

�ֿ� ������ ������ �����ϴ�.
���ҽ��׷� : TobeeRCGroup
Kubernetes Ŭ������ : TobeeCluster
�����̳� ������Ʈ�� : tobeereg

���� �α���

�켱 ������� ���� �Ͽ� ������ �α��� �մϴ�.
az login
Ȥ��
az login --use-device-code --debug


az aks get-credentials --resource-group TobeeRCGroup --name TobeeCluster
az aks get-credentials -g TobeeRCGroup -n TobeeCluster

az aks get-credentials -g TobeeRCGroup -n TobeeCluster

Ȯ�� �غ���
kubectl config current-context

Azure �����̳� ������Ʈ�� �α���
az acr login --name tobeereg

Azure Ŭ������(AKS)�� ������Ʈ��(ACR) ���̱�
az aks update -n TobeeCluster -g TobeeRCGroup --attach-acr tobeereg


����ũ�μ��� ���� �� ����

kubectl ��ġ
��ġ Ȯ��
kubectl version --client
��ġ �Ǿ� ���� ���� ���
- Kubectl ��ġ
�� sudo apt-get update
�� sudo apt-get install -y kubectl 

AKS ���� Ȯ��
�� kubectl config current-context
�� kubectl get all 



���� �����ϱ�
kubectl delete deploy,service --all
kubectl delete deploy,service,pod --all
kubectl delete deploy,service,pod,hpa --all
kubectl delete pvc --all


�ҽ� �ٿ�ε�
cd D:\DEV\Experiments\CloudLvl2_4
git clone https://github.com/tommybee-dev/food-delivery2

ȯ�� ����
batch\setenv.bat ������ �ڽ��� ȯ�濡 �µ��� �����մϴ�.

�ҽ� ���� �� ��Ű¡
batch �������� �����մϴ�.

```
setenv.bat
cd app
mvn clean package -Dmaven.test.skip=true

cd ..\app_mongo
mvn clean package -Dmaven.test.skip=true

cd ..\gateway
mvn clean package -Dmaven.test.skip=true

cd ..\pay
mvn clean package -Dmaven.test.skip=true

cd ..\store
mvn clean package -Dmaven.test.skip=true

cd ..\store_maria
mvn clean package -Dmaven.test.skip=true
```

��Ŀ �̹��� ���� �� ������Ʈ���� ���ε� �ϱ�

�ΰ��� ������� ���� �� �� ����

az acr build --registry [acr-registry-name] --image [acr-registry-name].azurecr.io/products:v1 .

Ȥ��

docker build -t [acr-registry-name].azurecr.io/products:v1 .
docker images
az acr login --name [acr-registry-name]
docker push [acr-registry-name].azurecr.io/products:v1

Dockerfile �ִ� ��ġ���� ���� ��ɾ�� ���� �ϸ� �˴ϴ�.


```
cd app
az acr build --registry tobeereg --image tobeereg.azurecr.io/app:v1 .

cd ../gateway
az acr build --registry tobeereg --image tobeereg.azurecr.io/gateway:v1 .

cd ../pay
az acr build --registry tobeereg --image tobeereg.azurecr.io/pay:v1 .

cd ../store
az acr build --registry tobeereg --image tobeereg.azurecr.io/store:v1 .
```

����DB�� ������DB�� �ʿ��� �ΰ��� ����ũ�μ��񽺴� �ϴ� �ڷ� �̷�ϴ�.
```
cd ../app_mongo
az acr build --registry TobeeReg --image TobeeReg.azurecr.io/app:v1 .

cd ../store_maria
az acr build --registry TobeeReg --image TobeeReg.azurecr.io/app:v1 .
```

���÷��̸�Ʈ �� ���� ����
```
cd ../../app/kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yaml

cd ../../pay/kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yaml

cd ../../store/kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yaml

cd ../../gateway/kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yaml
```

����, ������ �߻� ���� ��� ��ġ ���� 3����

�켱 POD�� ��ȸ �Ѵ�.
kubectl get pod

- describe �� ���

kubectl describe pod my-nginx-76cbb98994-hcdlz

- �α� Ȯ��
kubectl logs my-nginx-7477855886-jmcg6

- �����̳� ���� Ȯ��
kubectl exec -it my-nginx-7477855886-2grg2 -- /bin/bash

������ �˻�
kubectl get all --namespace=kafka


����ġī��ī Ŭ������
https://github.com/MicrosoftDocs/azure-docs/blob/master/articles/hdinsight/kafka/apache-kafka-get-started.md

����.
https://blog.naver.com/tommybee/222242516826

Trouble-shooting

�α��� �ÿ� ������ ���� ������ �ȵǴ� ���
https://blog.jhnr.ch/2018/05/16/working-with-azure-cli-behind-ssl-intercepting-proxy-server/
export AZURE_CLI_DISABLE_CONNECTION_VERIFICATION=anycontent
������� �ϸ� ���� �޽��� ��...


sudo cp azurepotal.cer /usr/local/share/ca-certificates/
sudo update-ca-certificates

sudo cp portalofficecom.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates

sudo cp stamp2loginmicrosoftonlinecom.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates

export REQUESTS_CA_BUNDLE=/etc/ssl/certs/ca-certificates.crt
export REQUESTS_CA_BUNDLE=/opt/az/lib/python3.6/site-packages/certifi/cacert.pem


openssl x509 -inform der -in azurepotal.cer -out azurepotal.pem
openssl x509 -inform der -in portalofficecom.crt -out portalofficecom.pem
openssl x509 -inform der -in stamp2loginmicrosoftonlinecom.crt -out stamp2loginmicrosoftonlinecom.pem

���� ������ ����
/opt/az/lib/python3.6/site-packages/certifi/cacert.pem
���� pem ������ ������ ��ģ��.


https://bluegreencity.github.io/azure/Azure-CLI-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%9C-%EC%97%90%EB%9F%AC%EB%B0%9C%EC%83%9D%ED%96%88%EC%9D%84-%EB%95%8C-%EB%8C%80%EC%B2%98%EB%B2%95/
https://docs.microsoft.com/ko-kr/cli/azure/install-azure-cli-windows?tabs=azure-cli

/opt/az/lib/python3.6/site-packages/urllib3/connectionpool.py

������ ���� �߻�
kubectl config view --raw -o jsonpath="{.clusters[?(@.name == 'TobeeCluster')].cluster.certificate-authority-data}" | base64 -d | openssl x509 -text | grep -A2 Validity
az aks create --resource-group TobeeRCGroup --name TobeeCluster --node-count 1 --enable-addons monitoring --generate-ssh-keys
