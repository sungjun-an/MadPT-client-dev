# git 프로젝트 관리

# 1. branch

## 1.1 master branch

마스터 브랜치 이며 실행 가능한 제품 상태의 버전, 최종 release 버전만 이 브랜치로 올립니다.
배포가 완료되면 태그로 버전을 붙입니다.

## 1.2 develop branch

개발 메인 브랜치 입니다. 이 개발 브랜치에서 특정 기능을 개발해야한다면 issue를 통해 feature branch를 만들어 해당 기능을 만든 다음 develop branch로 pull request를 넣어 merge합니다.
develop branch에서 배포 가능한 수준까지 개발이 된다면 release branch로 업로드하여 최종 release까지 점검을 진행합니다.

## 1.3 feature branches

기능 및 버그 픽스를 전담하는 브랜치로, develop 브랜치에서 특정 기능을 개발해야 할때 이 브랜치를 만들어 기능을 개발 후 develop branch로 pull request를 올립니다. **이 브랜치는 issue를 통해 생성됩니다.**
feature/{feature_name} 형태 입니다.

## 1.4 release branches

배포를 준비하는 브랜치로 develop 브랜치에서 개발이 완료되면 이 브랜치로 옮겨, 배포를 위한 준비를 합니다. 만약 추가 개발이 필요하다면 이 브랜치에서 다시 develop 브랜치로 옮겨 개발 후 다시 release branch로 옮깁니다. release/{version} 형태 입니다.


## 1.5 hotfix branches
마스터 브랜치로 업로드 되기 전까지 미처 발견되지 못한, 사용상에 생긴 버그들을 급하게 픽스해야할때 사용할 브랜치 입니다.

# 2. Github Actions

## workflow는 다음 조건에서 활성화 됩니다. 
* feature => develop PR closed시 빌드 테스트 workflow 진행 
* release push시 빌드, 배포 테스트 workflow 진행
* release => master PR closed시 빌드, 배포 진행

# 3. issue

## 3.1 issue 템플릿

title : `issue: ....`   
body : 
```md
# Description

---

# Todo
 
- [ ] todo 1
- [ ] todo 2

---

# Optional Comment
...
---
```

## 3.2 branch관리
앞으로는 issue를 통해 브랜치를 생성합니다.  
feature 브랜치는 develop으로부터 생성되어야 합니다.  

### 3.2.1 issue 생성
개발, 버그픽스등 필요한 이슈에 따라 github에서 issue를 생성합니다.

### 3.2.2 issue로부터 branch 생성
issue를 만든 후 issue => Development => Create a branch를 눌러 새로운 브랜치를 생성합니다.   
브랜치이름 형식은 `feature/{issue_id}_*`입니다.  
ex) `feature/13_develop_dev_api`  
이때 Change branch source를 눌러 Branch source를 develop으로 변경해 생성해 줍니다.  

### 3.2.3 로컬 호스트에서 remote fetch 및 적용
`3.2.2` 과정완료후 생성된 브랜치는 remote 에만 존재하므로 로컬 호스트에 브랜치를 가져와야 합니다.  
브랜치를 만들게 된다면 두줄의 코드를 받게되는데 해당 코드를 복사하여 로컬에 적용해 줍니다.  

```bash
git remote fetch
git checkout {만든 브랜치}
```

## 3.3 issue example

title : `issue: 개발용 api 개발`   
body : 
```md
# Description
실제 API 개발 전까지 사용할 개발용 API 개발  
개발용 API는 더미 데이터만을 제공  

# Todo
- [ ] 음식 데이터 가져오기 API
- [ ] 운동 리스트 가져오기 API
```

created branch : `feature/13_develop_dev_api`  

## 3.3.1 issue example 결과화면

# Description
실제 API 개발 전까지 사용할 개발용 API 개발   
개발용 API는 더미 데이터만을 제공   

# Todo
- [ ] 음식 데이터 가져오기 API  
- [ ] 운동 리스트 가져오기 API  


# 4. Pull Request

## 4.1 Pull Request 템플릿
title : `PR: title | {request_branch} => {target_branch}`  
body : 
```md
# 변경사항
...
---
# 
```
## 4.2 Pull Request merge comment 템플릿
열려있는 PR을 머지하고 닫을때 코멘트 양식입니다.  

title : `MERGE #PR_ID: title | {request_branch} => {target_branch}`  
body : 
```txt
추가사항 있을시 기입.
```

## 4.3 Pull Request example
title : `PR: 개발용 api 개발 완료 | feature/13_develop_dev_api => develop`  
body : 
```md
# 변경사항
개발용 api 추가
* 식단 리스트 가져오기 api
	/food/get-food-list-dev
...
```

### 4.3.1 Pull Request example 결과화면
#### `PR: 개발용 api 개발 완료 | feature/13_develop_dev_api => develop`  

# 변경사항
개발용 api 추가  
* 식단 리스트 가져오기 api  
/food/get-food-list-dev  


## 4.4 Pull Request Merge example
열려있는 PR을 머지하고 닫을때 코멘트 양식입니다.  
title : `MERGE #23: 개발용 api 개발 완료 | feature/13_develop_dev_api => develop`  
body : 
```txt
```

### 4.4.1 Pull Request Merge example 결과화면

#### `MERGE #23: 개발용 api 개발 완료 | feature/13_develop_dev_api => develop` 


# 5. commit

## 5.1 Commit Message 템플릿
```md
# 제목, 본문, 바닥글간 공백 한줄씩 주세요.
# #으로 시작하는 줄은 커밋으로 올라가지 않습니다.
########제목##########
# [커밋 유형] : [#id] [subject] 
## 커밋 유형
### FEAT : 새로운 기능의 추가
### FIX: 버그 수정
### DOCS: 문서 수정
### STYLE: 스타일 관련 기능(코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
### REFACTOR: 코드 리펙토링
### TEST: 테스트 코트, 리펙토링 테스트 코드 추가
### CHORE: 빌드 업무 수정, 패키지 매니저 수정(ex .gitignore 수정 같은 경우)
### CICD: DevOps관련 기능 수정

########본문##########

## 본문은 작성되면 좋지만 필수는 아닙니다!
## 본문은 진행된 내용을 잘 알아볼 수 있도록 작성해 주세요.
## ~~추가, ~~제거 등  추상적인 말은 지양
## 참고할만한 커밋이 있다면 해당 커밋의 해시태그 7자리를 같이 써주세요
## ex) b1dbf65 : Food 도메인의 멤버 이름 변경 food_name => foodName

########바닥글##########

## 바닥글은 참조할만한 이슈, PR, 커밋등을 추가하는 용도로 사용해 주세요
## ex) 만약 16번, 18번 이슈 관련된 커밋이라면 
## ref: #16, #18

## 만약 이슈가 해결되었다면 다음과 같이 작성해 주세요
## ex) 20번 이슈가 해결되었을때
## close #20
```

## 5.2 git commit 템플릿 적용
`config commit.template .github/GIT_COMMIT_TEMPLATE.md`

## 5.3 commit message example
주석은 올라가지 않습니다.
```txt
# 제목, 본문, 바닥글간 공백 한줄씩 주세요.
# #으로 시작하는 줄은 커밋으로 올라가지 않습니다.
########제목##########
FEAT : #25 개발용 api 컨트롤러 제작
########본문##########
개발용 api는 실제 api의 URL에 -dev를 붙여 사용합니다.
ex) 음식 데이터 가져오기 api 
URL : /food/get-food-list
개발용 URL : /food/get-food-list-dev
########바닥글##########
ref: #25

# 제목 작성 요령
# [커밋 유형] : [#id] [subject] 

## 커밋 유형

### FEAT : 새로운 기능의 추가
### FIX: 버그 수정
### DOCS: 문서 수정
### STYLE: 스타일 관련 기능(코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
### REFACTOR: 코드 리펙토링
### TEST: 테스트 코트, 리펙토링 테스트 코드 추가
### CHORE: 빌드 업무 수정, 패키지 매니저 수정(ex .gitignore 수정 같은 경우)
### CICD: DevOps관련 기능 수정

# 본문 작성 요령

## 본문은 진행된 내용을 잘 알아볼 수 있도록 작성해 주세요.
## ~~추가, ~~제거 등  추상적인 말은 지양
## 참고할만한 커밋이 있다면 해당 커밋의 해시태그 7자리를 같이 써주세요
## ex) b1dbf65 : Food 도메인의 멤버 이름 변경 food_name => foodName

# 바닥글 작성 요령

## 바닥글은 참조할만한 이슈, PR, 커밋등을 추가하는 용도로 사용해 주세요
## ex) 만약 16번, 18번 이슈 관련된 커밋이라면 
## ref: #16, #18

## 만약 이슈가 해결되었다면 다음과 같이 작성해 주세요
## ex) 20번 이슈가 해결되었을때
## close #20
```

### 5.3.1 결과화면

FEAT : #25 개발용 api 컨트롤러 제작

개발용 api는 실제 api의 URL에 -dev를 붙여 사용합니다.  
ex) 음식 데이터 가져오기 api   
URL : /food/get-food-list  
개발용 URL : /food/get-food-list-dev  
  
ref: #25


