# Mini Bank - Spring Boot Application

Bu proje, Spring Boot kullanarak geliştirilmiş bir **mini banka uygulaması**dır. Bakiye sorgulama, para yatırma, çekme ve para transferi gibi temel bankacılık işlemlerini gerçekleştiren bir backend uygulamasıdır.

## Özellikler

- **Bakiye Yönetimi**: Kullanıcılar hesaplarına para yatırabilir, çekebilir ve bakiye sorgulama yapabilir.
- **Para Transferi**: Bir kullanıcı başka bir kullanıcıya para transferi yapabilir.
- **Cache Kullanımı**: Performans optimizasyonu için Spring Cache kullanılarak bakiye sorgulamaları hızlandırılmıştır.

## Teknolojiler

- **Java** (JDK 17)
- **Spring Boot** (Veritabanı yönetimi için Spring Data JPA, Cache için Spring Cache)
- **Postgre SQL**
- **Spring Security** (Eğer güvenlik uygulandıysa)

## Başlarken

Bu projeyi çalıştırmak için aşağıdaki adımları takip edebilirsiniz:

### 1. Projeyi Çekin

```bash
git clone https://github.com/beyznur-e/mini_bank.git
```
### 2. Gerekli Bağımlılıkları Yükleyin
Proje Spring Boot ile yapılandırıldığı için, bağımlılıkları yüklemek için aşağıdaki komutu çalıştırabilirsiniz:
```bash
mvn clean install
```
### 3. Uygulamayı Çalıştırın
Aşağıdaki komut ile uygulamayı başlatabilirsiniz:
```bash
mvn spring-boot:run
```

Uygulama çalışmaya başladığında, aşağıdaki endpoint'ler üzerinden işlemler yapabilirsiniz:

API Endpoint'leri
- Kullanıcı Kaydı: POST /users
- Bakiye Görüntüle: GET /users/{userId}/balance
- Para Yatırma: POST /users/{userId}/yatir
- Para Çekme: POST /users/{userId}/cek
- Para Transferi: POST /users/{senderId}/transfer/{receiverId}
