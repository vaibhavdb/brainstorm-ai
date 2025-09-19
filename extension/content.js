(() => {
  if (window.location.hostname === "github.com") {
    const pathParts = window.location.pathname.split("/").filter(Boolean);
    if (pathParts.length >= 2) {
      const owner = pathParts[0];
      const repo = pathParts[1];
      chrome.runtime.sendMessage({ repoUrl: `https://github.com/${owner}/${repo}` });
    }
  }
})();
