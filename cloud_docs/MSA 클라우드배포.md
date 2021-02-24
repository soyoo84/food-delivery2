클라우드 환경 설정에서 받아온 주요 정보를 사용해서 MSA 를 구성 할 차례입니다.

주요 정보는 다음과 같습니다.

```
리소스그룹 : TobeeRCGroup
Kubernetes 클러스터 : TobeeCluster
컨테이너 레지스트리 : tobeereg
```

- 애저 로그인

우선 우분투를 실행 하여 애저에 로그인 합니다.
```
az login
혹은
az login --use-device-code --debug
```

다음 명령을 실행해서 credential을 얻습니다.
```
az aks get-credentials --resource-group TobeeRCGroup --name TobeeCluster
or
az aks get-credentials -g TobeeRCGroup -n TobeeCluster
```

- 확인 해보기
kubectl config current-context

- Azure 컨테이너 레지스트리 로그인
```
az acr login --name tobeereg
```

- Azure 클러스터(AKS)에 레지스트리(ACR) 붙이기
```
az aks update -n TobeeCluster -g TobeeRCGroup --attach-acr tobeereg
```

- 마이크로서비스 구성 및 설정

kubectl 설치

설치 확인
```
kubectl version --client
```
설치 되어 있지 않은 경우
- Kubectl 설치
```
sudo apt-get update
sudo apt-get install -y kubectl 
```

AKS 연결 확인
```
kubectl config current-context
kubectl get all 
```


- 서비스 정리하기
```
kubectl delete deploy,service --all
kubectl delete deploy,service,pod --all
kubectl delete deploy,service,pod,hpa --all
kubectl delete pvc --all
```

- 소스 다운로드
```
cd D:\DEV\Experiments\CloudLvl2_4
git clone https://github.com/tommybee-dev/food-delivery2
```

- 환경 설정
batch\setenv.bat 파일을 자신의 환경에 맞도록 수정합니다.

소스 빌드 및 패키징
batch 폴더에서 시작합니다.

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

- 도커 이미지 빌드 및 레지스트리에 업로드 하기

두가지 방식으로 진행 될 수 있음
```
az acr build --registry [acr-registry-name] --image [acr-registry-name].azurecr.io/products:v1 .
```
혹은
```
docker build -t [acr-registry-name].azurecr.io/products:v1 .
docker images
az acr login --name [acr-registry-name]
docker push [acr-registry-name].azurecr.io/products:v1
```

Dockerfile 있는 위치에서 다음 명령어로 진행 하면 됩니다.


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

몽고DB와 마리아DB가 필요한 두개의 마이크로서비스는 일단 뒤로 미룹니다.
```
cd ../app_mongo
az acr build --registry TobeeReg --image TobeeReg.azurecr.io/app:v1 .

cd ../store_maria
az acr build --registry TobeeReg --image TobeeReg.azurecr.io/app:v1 .
```

- 디플로이먼트 및 서비스 적용
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

- 오류가 발생에 대한 조치 사항 3가지

우선 POD를 조회 한 후,
```
kubectl get pod
```
- describe 를 사용하는 방법
```
kubectl describe pod my-nginx-76cbb98994-hcdlz
```

- 로그를 확인 하는 방법
```
kubectl logs my-nginx-7477855886-jmcg6
```

- 컨테이너 내부를 확인하는 방법
- 
```
kubectl exec -it my-nginx-7477855886-2grg2 -- /bin/bash
```

- 네임스페이스 검색
```
kubectl get all --namespace=kafka
```

- 아파치카프카 클러스터 관련

https://github.com/MicrosoftDocs/azure-docs/blob/master/articles/hdinsight/kafka/apache-kafka-get-started.md



- 참고 사이트

https://blog.naver.com/tommybee/222242516826

- Trouble-shooting

```
로그인 시에 오류가 나서 접속이 안되는 경우
https://blog.jhnr.ch/2018/05/16/working-with-azure-cli-behind-ssl-intercepting-proxy-server/
export AZURE_CLI_DISABLE_CONNECTION_VERIFICATION=anycontent
여기까지 하면 주의 메시지 뜸...


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

다음 파일을 연다
/opt/az/lib/python3.6/site-packages/certifi/cacert.pem
위의 pem 파일의 내용을 합친다.


https://bluegreencity.github.io/azure/Azure-CLI-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%9C-%EC%97%90%EB%9F%AC%EB%B0%9C%EC%83%9D%ED%96%88%EC%9D%84-%EB%95%8C-%EB%8C%80%EC%B2%98%EB%B2%95/
https://docs.microsoft.com/ko-kr/cli/azure/install-azure-cli-windows?tabs=azure-cli

/opt/az/lib/python3.6/site-packages/urllib3/connectionpool.py

인증서 오류 발생
kubectl config view --raw -o jsonpath="{.clusters[?(@.name == 'TobeeCluster')].cluster.certificate-authority-data}" | base64 -d | openssl x509 -text | grep -A2 Validity
az aks create --resource-group TobeeRCGroup --name TobeeCluster --node-count 1 --enable-addons monitoring --generate-ssh-keys
```
