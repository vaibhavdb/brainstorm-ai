document.getElementById("summarizeBtn").addEventListener("click", () => {
  chrome.tabs.query({ active: true, currentWindow: true }, (tabs) => {
    const activeTab = tabs[0];
    const repoUrl = activeTab.url;

    fetch("http://localhost:8080/api/v1/projects/summarize", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ repoUrl })
    })
      .then(res => res.json())
      .then(data => {
        document.getElementById("summary").textContent = data.summary;
      })
      .catch(err => {
        document.getElementById("summary").textContent = "Error: " + err.message;
      });
  });
});
