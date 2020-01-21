package org.shaneking.skavax.servlet.http;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class HttpServletRequestWrapper0 extends HttpServletRequestWrapper {
  @Getter
  private byte[] requestBytes = null;

  public HttpServletRequestWrapper0(HttpServletRequest request) {
    super(request);
    //Reference org.springframework.util.StreamUtils.copyToByteArray()
    try {
      if (request.getInputStream() == null) {
        requestBytes = new byte[0];
      } else {
        InputStream in = request.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
        out.flush();
        requestBytes = out.toByteArray();
      }
    } catch (IOException e) {
      log.error("Wrapper requestBytes failed.", e);
    }
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (requestBytes == null) {
      requestBytes = new byte[0];
    }
    final ByteArrayInputStream in = new ByteArrayInputStream(requestBytes);
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
        //do nothing
      }

      @Override
      public int read() throws IOException {
        return in.read();
      }
    };
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }
}
