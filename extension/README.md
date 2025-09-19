# CodeScribe - Minimal local version

## Prereqs
- Java 17+, Maven
- Node/Chrome for extension
- Set env var OPENAI_API_KEY
- (Optional) Set GITHUB_TOKEN to avoid API rate limits

## Run backend
cd backend
mvn spring-boot:run

## Load extension locally in Chrome
- Open chrome://extensions, enable Developer mode
- Load unpacked -> select the `extension/` folder
- Visit a GitHub repo page, click extension icon, then Summarize Repo.

Notes:
- This is a minimal proof-of-concept. It uses in-memory caching and no persistence.
- Costs: each file summary calls OpenAI. Use small repos first and set throttles if needed.
