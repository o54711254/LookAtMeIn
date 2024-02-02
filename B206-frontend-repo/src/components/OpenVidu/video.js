function createToken(sessionId) {
    let data = {};
    const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
    try {
      const response = fetch(
        `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
        {
          method: "POST",
          headers: {
            Authorization: `Basic ${btoa(
              `OPENVIDUAPP:${"lookatme"}`
            )}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        }
      );
  
      if (!response.ok) {
        throw new Error("Failed to create token");
      }
  
      const responseData = response.json();
      return responseData.token;
    } catch (error) {
      throw new Error(error.message);
    }
  }
  