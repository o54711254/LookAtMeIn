import { useState } from "react";

// Modal Component (간단한 모달 컴포넌트 예시)
function SuggestModal({ isOpen, onClose, onSubmit }) {
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(message);
    onClose(); // 모달 닫기
  };

  return isOpen ? (
    <div className="modalBackground">
      <div className="modalContainer">
        <button onClick={onClose}>닫기</button>
        <form onSubmit={handleSubmit}>
          <textarea
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="제안 메시지를 작성하세요"
            required
          />
          <button type="submit">제안하기</button>
        </form>
      </div>
    </div>
  ) : null;
}
export default SuggestModal;
