// import $ from "jquery";

// class OpenViduLayout {
//   layoutContainer; // 레이아웃 컨테이너
//   opts; // 옵션

//   // 요소의 종횡비를 수정하는 함수
//   fixAspectRatio(elem, width) {
//     const sub = elem.querySelector(".OT_root");
//     if (sub) {
//       const oldWidth = sub.style.width;
//       sub.style.width = width + "px";
//       sub.style.width = oldWidth || "";
//     }
//   }

//   // 요소를 위치시키는 함수
//   positionElement(elem, x, y, width, height, animate) {
//     const targetPosition = {
//       left: x + "px",
//       top: y + "px",
//       width: width + "px",
//       height: height + "px",
//     };

//     this.fixAspectRatio(elem, width);

//     if (animate && $) {
//       $(elem).stop();
//       $(elem).animate(
//         targetPosition,
//         animate.duration || 200,
//         animate.easing || "swing",
//         () => {
//           this.fixAspectRatio(elem, width);
//           if (animate.complete) {
//             animate.complete.call(this);
//           }
//         }
//       );
//     } else {
//       $(elem).css(targetPosition);
//     }
//     this.fixAspectRatio(elem, width);
//   }

//   // 비디오 종횡비를 가져오는 함수
//   getVideoRatio(elem) {
//     if (!elem) {
//       return 3 / 4;
//     }
//     const video = elem.querySelector("video");
//     if (video && video.videoHeight && video.videoWidth) {
//       return video.videoHeight / video.videoWidth;
//     } else if (elem.videoHeight && elem.videoWidth) {
//       return elem.videoHeight / elem.videoWidth;
//     }
//     return 3 / 4;
//   }

//   // CSS 속성 값을 숫자로 가져오는 함수
//   getCSSNumber(elem, prop) {
//     const cssStr = $(elem).css(prop);
//     return cssStr ? parseInt(cssStr, 10) : 0;
//   }

//   // 랜덤 UUID를 생성하는 함수
//   cheapUUID() {
//     return (Math.random() * 100000000).toFixed(0);
//   }

//   // 요소의 높이를 가져오는 함수
//   getHeight(elem) {
//     const heightStr = $(elem).css("height");
//     return heightStr ? parseInt(heightStr, 10) : 0;
//   }

//   // 요소의 너비를 가져오는 함수
//   getWidth(elem) {
//     const widthStr = $(elem).css("width");
//     return widthStr ? parseInt(widthStr, 10) : 0;
//   }

//   // 최적의 차원을 계산하는 함수
//   getBestDimensions(minR, maxR, count, WIDTH, HEIGHT, targetHeight) {
//     let maxArea, targetCols, targetRows, targetWidth, tWidth, tHeight, tRatio;

//     for (let i = 1; i <= count; i++) {
//       const colsAux = i;
//       const rowsAux = Math.ceil(count / colsAux);
      
//       tHeight = Math.floor(HEIGHT / rowsAux);
//       tWidth = Math.floor(WIDTH / colsAux);

//       tRatio = tHeight / tWidth;
//       if (tRatio > maxR) {
//         tRatio = maxR;
//         tHeight = tWidth * tRatio;
//       } else if (tRatio < minR) {
//         tRatio = minR;
//         tWidth = tHeight / tRatio;
//       }

//       const area = tWidth * tHeight * count;

//       if (maxArea === undefined || area > maxArea) {
//         maxArea = area;
//         targetHeight = tHeight;
//         targetWidth = tWidth;
//         targetCols = colsAux;
//         targetRows = rowsAux;
//       }
//     }
//     return {
//       maxArea: maxArea,
//       targetCols: targetCols,
//       targetRows: targetRows,
//       targetHeight: targetHeight,
//       targetWidth: targetWidth,
//       ratio: targetHeight / targetWidth,
//     };
//   }

//   // 요소들을 배열하는 함수
//   arrange(
//     children,
//     WIDTH,
//     HEIGHT,
//     offsetLeft,
//     offsetTop,
//     fixedRatio,
//     minRatio,
//     maxRatio,
//     animate
//   ) {
//     let targetHeight;

//     const count = children.length;
//     let dimensions;

//     if (!fixedRatio) {
//       dimensions = this.getBestDimensions(
//         minRatio,
//         maxRatio,
//         count,
//         WIDTH,
//         HEIGHT,
//         targetHeight
//       );
//     } else {
//       const ratio = this.getVideoRatio(
//         children.length > 0 ? children[0] : null
//       );
//       dimensions = this.getBestDimensions(
//         ratio,
//         ratio,
//         count,
//         WIDTH,
//         HEIGHT,
//         targetHeight
//       );
//     }

//     let x = 0,
//       y = 0;
//     const rows = [];
//     let row;
//     for (let i = 0; i < children.length; i++) {
//       if (i % dimensions.targetCols === 0) {
//         row = {
//           children: [],
//           width: 0,
//           height: 0,
//         };
//         rows.push(row);
//       }
//       const elem = children[i];
//       row.children.push(elem);
//       let targetWidth = dimensions.targetWidth;
//       targetHeight = dimensions.targetHeight;
//       if (fixedRatio) {
//         targetWidth = targetHeight / this.getVideoRatio(elem);
//       }
//       row.width += targetWidth;
//       row.height = targetHeight;
//     }

//     let totalRowHeight = 0;
//     let remainingShortRows = 0;
//     for (let i = 0; i < rows.length; i++) {
//       row = rows[i];
//       if (row.width > WIDTH) {
//         row.height = Math.floor(row.height * (WIDTH / row.width));
//         row.width = WIDTH;
//       } else if (row.width < WIDTH) {
//         remainingShortRows += 1;
//       }
//       totalRowHeight += row.height;
//     }
//     if (totalRowHeight < HEIGHT && remainingShortRows > 0) {
//       let remainingHeightDiff = HEIGHT - totalRowHeight;
//       totalRowHeight = 0;
//       for (let i = 0; i < rows.length; i++) {
//         row = rows[i];
//         if (row.width < WIDTH) {
//           let extraHeight = remainingHeightDiff / remainingShortRows;
//           if (extraHeight / row.height > (WIDTH - row.width) / row.width) {
//             extraHeight = Math.floor(
//               ((WIDTH - row.width) / row.width) * row.height
//             );
//           }
//           row.width += Math.floor((extraHeight / row.height) * row.width);
//           row.height += extraHeight;
//           remainingHeightDiff -= extraHeight;
//           remainingShortRows -= 1;
//         }
//         totalRowHeight += row.height;
//       }
//     }
//     let accumulatedHeight = offsetTop;
//     for (let i = 0; i < rows.length; i++) {
//       row = rows[i];
//       let accumulatedWidth = offsetLeft;
//       for (let j = 0; j < row.children.length; j++) {
//         const child = row.children[j];
//         this.positionElement(
//           child,
//           accumulatedWidth,
//           accumulatedHeight,
//           row.width,
//           row.height,
//           animate
//         );
//         accumulatedWidth += row.width;
//       }
//       accumulatedHeight += row.height;
//     }
//   }
// }

// export default OpenViduLayout;
