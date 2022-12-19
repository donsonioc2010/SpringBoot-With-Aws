#!/usr/bin/env bash

# 쉬고 있는 profile 찾는 기능, : real1이 사용중이라면 real2가 쉬고있으니 real2를 설정

function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] #400보다 큰 값(즉 4, 5오류 의미)

  then
      CURRENT_PROFILE=real2
  else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}