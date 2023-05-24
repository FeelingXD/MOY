## Docker-elk-kor(FeelingXD)

---

원본 (doker-elk)-> https://github.com/deviantony/docker-elk
fork (doker-elk-kor) -> https://github.com/ksundong/docker-elk-kor

이 docker-compose 파일은 위의 레포들을 기반으로 커스텀하여 만들었습니다.
변경해서 사용 할 경우 위의 레포를 찾아보시는걸 권장드립니다.

## use:

bash : ~> docker-compose up

## include

- docker
- mysql
- mysqljdbc(for logstash)
- logstash
- elasticsearch
- kibana

.env:
환경변수가 등록된 곳입니다. 이곳에서 커스텀할수있습니다.
