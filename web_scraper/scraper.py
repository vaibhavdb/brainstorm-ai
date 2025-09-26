import os
import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin


URL = "https://en.wikipedia.org/wiki/Web_scraping"   # Change this to your target site
OUTPUT_DIR = "output"

os.makedirs(OUTPUT_DIR, exist_ok=True)
os.makedirs(os.path.join(OUTPUT_DIR, "images"), exist_ok=True)


print(f"Fetching {URL} ...")
response = requests.get(URL, headers={"User-Agent": "Mozilla/5.0"})
response.raise_for_status()

html_path = os.path.join(OUTPUT_DIR, "page.html")
with open(html_path, "w", encoding="utf-8") as f:
    f.write(response.text)
print(f"✅ Saved HTML to {html_path}")


soup = BeautifulSoup(response.text, "html.parser")

# Extract text content (paragraphs + headings)
text_content = []
for tag in soup.find_all(["h1", "h2", "h3", "p"]):
    text_content.append(tag.get_text(strip=True))

text_path = os.path.join(OUTPUT_DIR, "content.txt")
with open(text_path, "w", encoding="utf-8") as f:
    f.write("\n\n".join(text_content))
print(f"✅ Saved text content to {text_path}")

# Extract all links
links = [urljoin(URL, a.get("href")) for a in soup.find_all("a", href=True)]
links_path = os.path.join(OUTPUT_DIR, "links.txt")
with open(links_path, "w", encoding="utf-8") as f:
    f.write("\n".join(links))
print(f"✅ Saved {len(links)} links to {links_path}")


images = [urljoin(URL, img.get("src")) for img in soup.find_all("img", src=True)]

for i, img_url in enumerate(images, start=1):
    try:
        img_data = requests.get(img_url, headers={"User-Agent": "Mozilla/5.0"}).content
        img_name = os.path.join(OUTPUT_DIR, "images", f"image_{i}.jpg")
        with open(img_name, "wb") as f:
            f.write(img_data)
    except Exception as e:
        print(f"❌ Failed to download {img_url}: {e}")

print(f"✅ Downloaded {len(images)} images into {OUTPUT_DIR}/images")
