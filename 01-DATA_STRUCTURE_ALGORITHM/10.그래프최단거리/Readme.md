# Pathfinding algorithm

## 그래프에서, 최단거리를 구하는 방법에 대해 설명해 주세요.

### Dijkstra Algorithm

- 특정 하나의 정점에서 다른 모든 정점으로 가는 최단 경로 탐색 알고리즘

  [Dijkstra](dijkstra//Dijkstra.md)

  O(V + E)log V

### Bellman Ford Algorithm

- 간선의 음수일 때 최단 경로를 구하지 못하는 Dijkstra 알고리즘의 한계점을 보안하기 위해 만들어졌다
- 매 단계마다 모든 노드를 탐색한다

  [Bellman Ford](bellman%20ford/BellmanFord.md)

  O(VE)

### Floyd Warshall

- 2차원 테이블에 최단거리를 저장(모든 지점에서 다른 지점까지의 최단거리)

  [Floyd Warshall](https://velog.io/@kimdukbae/%ED%94%8C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%9B%8C%EC%85%9C-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-Floyd-Warshall-Algorithm)

  O(N^3)

## 트리에서는 어떤 방식으로 최단거리를 구할 수 있을까요? (위 방법을 사용하지 않고)

[MST](/CS-712/01-DATA_STRUCTURE_ALGORITHM/12_MST/Readme.md)

## 다익스트라 알고리즘에서, 힙을 사용하지 않고 구현한다면 시간복잡도가 어떻게 변화할까요?

최단거리 노드를 찾기 O(V)  
작업의 반복 O(V)  
따라서 `O(V^2)`의 시간복잡도가 발생한다

## 정점의 개수가 N개, 간선의 개수가 N^3 개라면, 어떤 알고리즘이 효율적일까요?

밀도가 매우 높은 그래프로 간선의 개수에 영향을 받지 않는 Floyd Warshall을 사용해야한다

## A\* 알고리즘에 대해 설명해 주세요. 이 알고리즘은 다익스트라와 비교해서 어떤 성능을 낼까요?

> 그래프 탐색 및 경로 찾기 알고리즘

- Dijkstra 알고리즘원리를 차용했다
- 시작노드와 목적지 노드를 분명하게 지정해 최단경로를 파악한다

```
f(x) = g(x) + h(x)
최소가 되는 지점을 우선탐색

g(x) => 현재 상태의 배용
h(x) => 다음상태로 이동할 때의 휴리스틱 함수(추정치)
```

1. f(x)를 오름차순 우선순위 큐에 노드로 삽입
2. 우선순위 큐에서 최우선 노드를 pop
3. 해당 노드에서 이동할 수 있는 노드 탐색
4. 그 노드들의 f(x)를 구함
5. 그 노드들을 우선수위 큐에 삽입
6. 목표 노드에 도달 할 때까지 반복

참고 : [A\* algorithm](http://www.gisdeveloper.co.kr/?p=3897)

## 음수 간선이 있을 때와, 음수 사이클이 있을 때 각각 어떤 최단거리 알고리즘을 사용해야 하는지 설명해 주세요.

> bellman Ford Algorithm

---

- https://roytravel.tistory.com/340
-
