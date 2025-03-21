# Hướng dẫn cách build và run MakeNBreak

Bước đầu tiên là hãy clone project về, paste lệnh này vào terminal
```
git clone https://github.com/CaoHuuKhuongDuy/MakeNBreak.git
```

## Tải JavaFX
Đầu tiên ta cần phải tải thư viện JavaFx và lưu vào trong thư mục project. 

- Link cho máy mac sài chip intel: https://download2.gluonhq.com/openjfx/23.0.1/openjfx-23.0.1_osx-x64_bin-sdk.zip 
- Link cho máy mac sài chip M: https://download2.gluonhq.com/openjfx/23.0.1/openjfx-23.0.1_osx-aarch64_bin-sdk.zip

Giải nén và để vào thư folder MakeNBreak

## Build MakeNBreak

Để thuận tiện trong việc code và compile trong project, mình recommend nên cài đặt Make về máy để chạy file Makefile hoặc sử dụng IntelliJ
### Hướng dẫn tải Make
Nếu máy chưa có Homebrew thì dùng câu lệnh dưới này để tải
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Check coi Homebrew đã được cài đặt chưa
```
brew --version
```
Nếu nó hiển thị version của Homebrew thì ok, ta qua bước cài đặt make, chạy câu lệnh

```
brew install make
```
Và tương tự dùng câu lệnh dưới đây để check coi make đã khởi tạo thành công chưa
```
make --version
```

Sau khi install make, ta chỉ cần `cd` vào thư mục MakeNBreak và run
```
make run
```

Note: Ngoài ra ta có thể config JavaFX trên IntelliJ khỏi cần dùng đến Makefile. GPT để biết rõ hơn..

## Run MakeNBreak
Mac:
```
java --module-path javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml -jar out/artifacts/MakeNBreak_jar/MakeNBreak.jar
```
Windows:
```
java --module-path javafx-sdk-23.0.1-window/lib --add-modules javafx.controls,javafx.fxml -jar out/artifacts/MakeNBreak_jar/MakeNBreak.jar
```