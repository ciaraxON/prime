package com.example.prime.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeUiController {

    @GetMapping(value = "/api/primes/ui", produces = MediaType.TEXT_HTML_VALUE)
    public String ui() {
        return """
            <!doctype html>
            <html lang="en">
            <head>
              <meta charset="utf-8"/>
              <meta name="viewport" content="width=device-width,initial-scale=1"/>
              <title>Prime Generator UI</title>
              
              <!-- page styling -->
              <style>
                body { font-family: Arial, sans-serif; max-width: 900px; margin: 2rem auto; padding: 0 1rem; background: #FFBDF3; color: #333; }
                label { display:block; margin-top: .5rem; }
                input, select, button { margin-top:.25rem; padding:.4rem; }
                pre { background:#f6f8fa; padding:1rem; overflow:auto; white-space:pre-wrap; }
                .meta { margin-top:.5rem; color:#555; }
              </style>
              
            </head>
            <body>
              <h1>Prime Generator</h1>
              
              <!-- form to collect user input -->
              <form id="primeForm">
                <label>
                  Please enter a whole number greater than or equal to 2:
                  <input type="number" name="limit" id="limit" inputmode="numeric" pattern="\\d+"/>
                </label>
                <label>
                  Algorithm (optional)
                  <select name="algorithm" id="algorithm">
                    <option value="">(auto)</option>
                    <option value="TRIAL_DIVISION">TRIAL_DIVISION</option>
                    <option value="SIEVE_OF_ERATOSTHENES">SIEVE_OF_ERATOSTHENES</option>
                    <option value="PARALLEL">PARALLEL</option>
                    <option value="SEGMENTED_SIEVE">SEGMENTED_SIEVE</option>
                  </select>
                </label>
                <label>
                  Format
                  <select name="format" id="format">
                    <option value="json">json</option>
                    <option value="xml">xml</option>
                  </select>
                </label>
                <div style="margin-top:.75rem">
                  <button type="submit">Generate</button>
                  <button type="button" id="clear">Clear</button>
                </div>
              </form>

              <div class="meta">Calls <code>/api/primes</code> and shows the result below.</div>

              <h2>Result</h2>
              <div id="status"></div>
              <pre id="output"></pre>

              <script>
                const form = document.getElementById('primeForm');
                const output = document.getElementById('output');
                const status = document.getElementById('status');
                const clear = document.getElementById('clear');

                //clear output button
                clear.addEventListener('click', () => {
                  output.textContent = '';
                  status.textContent = '';
                });
                
                //form submission
                form.addEventListener('submit', async (e) => {
                  e.preventDefault();
                  output.textContent = '';
                  status.textContent = 'Loading...';
                  const limit = document.getElementById('limit').value;
                  const algorithm = document.getElementById('algorithm').value;
                  const format = document.getElementById('format').value;

                  const params = new URLSearchParams();
                  params.append('limit', limit);
                  if (algorithm) params.append('algorithm', algorithm);
                  if (format) params.append('format', format);

                  //make API request
                  try {
                    const accept = format === 'xml' ? 'application/xml' : 'application/json';
                    const res = await fetch('/api/primes?' + params.toString(), { headers: { 'Accept': accept }});
                    const text = await res.text();
                    status.textContent = 'HTTP ' + res.status;
                    
                    //display response based on content type
                    if (res.headers.get('content-type')?.includes('application/json')) {
                      try {
                        const obj = JSON.parse(text);
                        output.textContent = JSON.stringify(obj, null, 2);
                      } catch (err) {
                        output.textContent = text;
                      }
                    } else {
                      output.textContent = text;
                    }
                  } catch (err) {
                    status.textContent = 'Request failed: ' + err;
                  }
                });
              </script>
            </body>
            </html>
            """;
    }
}
