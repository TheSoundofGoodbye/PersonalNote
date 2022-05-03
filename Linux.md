1. CentO 설치 시 
 - nmtui 로 네트워크 설정
 - EPEL 패키지 설치 yum install epel-release
---
2. epel-release 설치 시 생기는 문제 
 - 패키지 설치 후 yum 이 repositories에 접근을 못하는 일이 발생
 - vi 등 에디터를 이용하여 /etc/yum.repo.d/epel.repo 를 열어보면
 - baseurl이 잘못되어있거나 mirrorlist가 "https" 로 되어있을 경우 발생
 - baseurl을 유효한 url로 바꿔주거나 https -> http 로 변경시 정상 작동
---
3. 외장 storage 마운트 시 
 - fdisk -l 로 연결되어(설치되어)있는 디바이스를 확인
 - mount /dev/sdb /tmp/usb (마운트할 디바이스, 마운트포인트 디렉토리)
 - 언마운트는 umount /dev/sdb
---
4. ntfs 파일시스템 마운트 시 
 - yum install ntfs-3g 패키지 설치
 - 그런데 안되는 경우가 있을 시 
 - 해당 usb나 외장하드를 연결한 윈도우 시스템에서 
 - '제어판\하드웨어 및 소리\전원 옵션' 메뉴 진입
 - '전원 단추 정의 및 암호 보호 설정' -> '빠른 시작 켜기' 를 체크 해제한다 (해당 저장장치를 연결한 상태로)
 - ntfsfix를 사용하는 경우도 있으니 원인을 잘 체크할 것
---
