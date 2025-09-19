import React from "react";

export default function ProjectView({ data }) {
  if (!data) return null;

  const { projectSummary, fileSummaries, repo, branch, generatedAt } = data;

  return (
    <div>
      <h2>
        {repo} <small style={{ fontSize: 12, color: "#777" }}>({branch})</small>
      </h2>
      <div style={{ padding: 12, background: "#f9f9f9", borderRadius: 6 }}>
        <pre style={{ whiteSpace: "pre-wrap" }}>{projectSummary}</pre>
        <div style={{ fontSize: 12, color: "#666", marginTop: 6 }}>
          Generated at: {generatedAt}
        </div>
      </div>

      <h3 style={{ marginTop: 16 }}>Files ({fileSummaries?.length ?? 0})</h3>
      <div>
        {fileSummaries?.map((f, idx) => (
          <details key={idx} style={{ marginBottom: 8 }}>
            <summary style={{ cursor: "pointer" }}>
              {typeof f === "string" ? f : f.path} — chunks:{" "}
              {typeof f === "string" ? "n/a" : f.chunks}
            </summary>
            <pre style={{ whiteSpace: "pre-wrap", padding: 8, background: "#fafafa" }}>
              {typeof f === "string" ? f : f.summary}
            </pre>
          </details>
        ))}
      </div>
    </div>
  );
}
