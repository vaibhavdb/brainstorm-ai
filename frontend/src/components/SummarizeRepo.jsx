import React, { useState, useEffect } from "react";

function SummarizeRepo() {
  const [repoUrl, setRepoUrl] = useState("");
  const [jobId, setJobId] = useState(null);
  const [result, setResult] = useState("No result yet");

  const startJob = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/v1/projects/jobs/start", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ repoUrl }),
      });

      if (!res.ok) {
        throw new Error(await res.text());
      }

      const data = await res.json();
      setJobId(data.jobId);
      setResult("Job started, waiting for summary...");
    } catch (err) {
      alert("Failed to start job: " + err.message);
    }
  };

  useEffect(() => {
    if (!jobId) return;

    const interval = setInterval(async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/api/v1/projects/jobs/${jobId}/status`
        );

        if (!res.ok) {
          throw new Error(await res.text());
        }

        const data = await res.json();
        if (data.status === "DONE") {
          setResult(data.result);
          clearInterval(interval);
        } else if (data.status === "FAILED") {
          setResult("Job failed: " + data.result);
          clearInterval(interval);
        }
      } catch (err) {
        setResult("Error fetching status: " + err.message);
        clearInterval(interval);
      }
    }, 3000);

    return () => clearInterval(interval);
  }, [jobId]);

  return (
    <div style={{ padding: 20 }}>
      <h2>Summarize GitHub Repo</h2>
      <input
        type="text"
        placeholder="Enter repo URL"
        value={repoUrl}
        onChange={(e) => setRepoUrl(e.target.value)}
        style={{ width: "300px", marginRight: "10px" }}
      />
      <button onClick={startJob}>Start Summarize Job</button>
      <p>
        <b>Result:</b> {result}
      </p>
    </div>
  );
}

export default SummarizeRepo;
