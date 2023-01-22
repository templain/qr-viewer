# qr-viewer
Java ライブラリ ZXingでQRコードを生成するWEBアプリケーション

![スクリーンショット](https://user-images.githubusercontent.com/13389772/213898434-aba0a049-902f-4369-8c3b-4fa75ff18cca.png)

## Getting Started

1. Dockerコンテナを立ち上げる

```bash
git clone https://github.com/templain/qr-viewer.git
cd qr-viewer
docker build -t qrcode-viewer .
docker run -d --name qrcode-viewer -p=8080:8080 qrcode-viewer 
```
2. ブラウザでページを表示する

`http://localhost:8080/qrcode-viewer/`をブラウザで開く
