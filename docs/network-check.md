# 설비 DHCP/아이피 확인 방법 (직결 테스트용)

아래 명령들은 **노트북/PC에서 실행**하는 기준입니다. 설비 자체 화면 또는 설정 메뉴가 있다면, 장비 UI에서 네트워크 상태를 확인하는 것이 가장 확실합니다.

## 1) 설비가 DHCP를 받았는지 확인하는 방법

### Linux
- **DHCP 요청/응답이 오가는지 확인**
  ```bash
  sudo tcpdump -ni <인터페이스명> port 67 or port 68
  ```
  - `DHCPDISCOVER`, `DHCPOFFER`, `DHCPREQUEST`, `DHCPACK` 로그가 보이면 DHCP가 정상으로 동작합니다.
- **DHCP 임대(lease) 파일 확인**
  ```bash
  sudo cat /var/lib/dhcp/dhclient*.leases
  ```
  - `lease { ... }` 블록 안에 `fixed-address`가 설비에 할당된 IP입니다.

### Windows
- **DHCP 상태 확인**
  ```cmd
  netsh interface ip show config name="Ethernet"
  ```
  - `DHCP enabled: Yes`가 표시되면 DHCP 모드입니다.
- **DHCP 임대 정보 확인**
  ```cmd
  ipconfig /all
  ```
  - 해당 어댑터의 `DHCP Enabled`, `DHCP Server`, `Lease Obtained` 항목으로 DHCP 수신 여부를 확인합니다.

## 2) 설비 아이피(IP) 확인 방법

### A. 동일 대역에서 ARP/Neighbor 테이블 확인
- **Linux**
  ```bash
  ip neigh
  ```
  - `192.168.x.y dev <iface> lladdr <MAC>` 형태로 나타난 항목이 설비 IP 후보입니다.
- **Windows**
  ```cmd
  arp -a
  ```

### B. 특정 대역 스캔 (직결 환경에서 추천)
> 직결 환경에서는 범위가 좁으니 `/24` 대역을 빠르게 확인할 수 있습니다.
- **Linux (nmap 설치 시)**
  ```bash
  sudo nmap -sn 192.168.0.0/24
  ```
- **Windows (PowerShell)**
  ```powershell
  1..254 | % { Test-Connection 192.168.0.$_ -Count 1 -Quiet } 
  ```

### C. 설비가 고정 IP로 세팅된 경우
- 설비 문서나 설정 화면에서 **고정 IP 주소를 직접 확인**해야 합니다.
  - 예: `192.168.0.19`처럼 고정값이 안내되어 있으면 그 값을 사용합니다.

## 3) 직결 테스트 팁
- **DHCP 서버가 없으면** 설비가 DHCP를 못 받을 수 있으므로, 노트북을 **고정 IP**로 설정하고 같은 대역에서 통신 여부를 확인합니다.
- 노트북을 `192.168.0.19/24`로 고정하는 경우, 설비는 DHCP로 같은 대역의 다른 IP를 받아야 통신이 가능합니다.

## 4) 내장 DHCP 서버 사용(노트북에서 자동 할당)
이 애플리케이션은 간단한 DHCP 서버를 내장하고 있으며, 노트북을 DHCP 서버로 동작시켜 설비에 IP를 자동 할당할 수 있습니다.
- DHCP 서버는 기본적으로 **포트 67**을 사용하므로, 실행 권한(관리자/루트)이 필요할 수 있습니다.
- 기본 DHCP 서버 IP: 노트북 고정 IP(`192.168.0.19`)
- 기본 제공 IP: `192.168.0.10`
- 변경은 아래 시스템 프로퍼티 또는 환경변수로 가능합니다.
  - `zes.dhcp.enabled` / `ZES_DHCP_ENABLED` (기본 true)
  - `zes.dhcp.server.ip` / `ZES_DHCP_SERVER_IP`
  - `zes.dhcp.offer.ip` / `ZES_DHCP_OFFER_IP`
  - `zes.dhcp.subnet.mask` / `ZES_DHCP_SUBNET_MASK`
  - `zes.dhcp.gateway` / `ZES_DHCP_GATEWAY`
  - `zes.dhcp.lease.seconds` / `ZES_DHCP_LEASE_SECONDS`
