import React from 'react';
import styles from './FreeBoardSearch.module.css'; // 스타일시트 경로는 실제 경로에 맞게 수정해주세요.
import profile from '../../../assets/gun.png'; // 프로필 이미지 경로는 실제 경로에 맞게 수정해주세요.
import FreeBoardRegist from '../../FreeBoard/FreeBoardRegist'; // FreeBoardRegist 컴포넌트 경로는 실제 경로에 맞게 수정해주세요.

const FreeBoardComponent = ({ results }) => {
  // 게시글 상세 페이지로 이동하는 함수
  const goDetailPage = (results) => {
    console.log(`Navigating to details of ${results.freeboardSeq}`);
    // 여기에 실제 페이지 이동 로직 추가
    // 예: navigate(`/freeboard/detail/${freeboardSeq}`);
  };

  return (
    <>
      <div>
        {results.map((board, index) => (
          <li
            key={index}
            onClick={() => goDetailPage(board.freeboardSeq)}
            className={styles.reviewItem}
          >
            <div>No. {index + 1}</div>
            <div>
              <img src={profile} alt="프로필" className={styles.profile} />
            </div>
            <div className={styles.writer}>
              <div>{board.userId}</div>
            </div>
            <div className={styles.title}>
              <div>{board.freeboardTitle}</div>
            </div>
            <div className={styles.date}>{board.freeboardRegisterdate}</div>
          </li>
        ))}
        <FreeBoardRegist />
      </div>
    </>
  );
};

export default FreeBoardComponent;
