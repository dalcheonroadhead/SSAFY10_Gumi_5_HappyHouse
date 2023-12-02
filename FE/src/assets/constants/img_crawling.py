from urllib.request import urlopen
from urllib.request import urlretrieve
from urllib.parse import quote_plus
from bs4 import BeautifulSoup
from selenium import webdriver

# url을 사용자 인풋 받아서 완성
search = input('검색어를 입력하세요.') 
url = f'https://www.google.com/search?q={quote_plus(search)}&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiQjv2hsMX9AhWDHXAKHbhLCXcQ_AUoAXoECAEQAw&biw=958&bih=959&dpr=1'

# 열린 크롬을 driver란 변수에 저장
driver = webdriver.Chrome()
#크롬에서 해당 url 조회
driver.get(url)

#반복 0~10000까지 스크롤
for i in range(500):
    driver.execute_script("window.scrollBy(0,10000)")
#스크롤 한 낱개의 페이지 소스를 html에 저장
html = driver.page_source

# soup에 beautifulsoup로 parsing 한 html 저장(시체(html)를 전부 분해해서 soup에 저장 -> 부품 찾기 쉽게)
soup = BeautifulSoup(html)
# 분해된 html중 ()안과 클래스 이름이 같은 녀석들만 img에 넣어라 
img = soup.select('.rg_i.Q4LuWd')
n= 1
imgurl = []

# 이미지를 하나씩 배열에 넣음. 
for i in img:
    try:
        imgurl.append(i.attrs["src"])
    except KeyError:
        imgurl.append(i.attrs["data-src"])

# 파일 저장
for i in imgurl:
    urlretrieve(i, "C:/Users/SSAFY/Desktop/Crawling/" + "building" + str(n) + ".jpg" )
    n+= 1
    print(imgurl)

driver.close()