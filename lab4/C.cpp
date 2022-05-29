#include <algorithm>
#include <array>
#include <bitset>
#include <cassert>
#include <climits>
#include <cstdint>
#include <cmath>
#include <complex>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <functional>
#include <iomanip>
#include <iostream>
#include <map>
#include <memory>
#include <numeric>
#include <queue>
#include <random>
#include <set>
#include <stack>
#include <string>
#include <unordered_set>
#include <unordered_map>
#include <ctime>
#include <vector>

#define int int32_t

typedef int64_t int64;
typedef uint64_t uint64;

using namespace std;

class ArraysUtils {
public:
    template <typename T>
    static void printArray(const vector<T>& array) {
      for (auto el : array) {
        cout << el << ' ';
      }
      cout << '\n';
    }

    template <typename T>
    static vector<T> nextArray(size_t len) {
      vector<T> result(len);
      for (size_t i = 0; i < len; i++) {
        cin >> result[i];
      }
      return result;
    }
};

namespace Utils {
    int64 max(int64 a, int64 b, int64 c) {
      return std::max(a, std::max(b, c));
    }

    int64 max(int64 a, int64 b) {
      return std::max(a, b);
    }
}

struct Node {
    int64 seg, pref, suf, sum;

    Node(int64 seg, int64 pref, int64 suf, int64 sum) {
      this->seg = seg;
      this->pref = pref;
      this->suf = suf;
      this->sum = sum;
    }

    static Node merge(const Node &first, const Node &second) {
      using namespace Utils;
      return {
              max(first.seg, second.seg, first.suf + second.pref),
              max(first.pref, first.sum + second.pref),
              max(second.suf, second.sum + first.suf),
              first.sum + second.sum
              };
    }

    static Node init(int value) {
      int res = max(value, 0);
      return {res, res, res, value};
    }
};

namespace SegTree {
    class SegmentTree {
    private:
        vector<Node> tree;
        size_t size;
        Node NEUTRAL = Node(0, 0, 0, 0);
        Node leftAccumulator = NEUTRAL, rightAccumulator = NEUTRAL;

        void init(size_t n) {
          size = 1;
          while (size < n) {
            size <<= 1;
          }
          tree.assign((size << 1) - 1, NEUTRAL);
        }

        size_t getMiddle(size_t left, size_t right) {
          return ((right - left) >> 1) + left;
        }

        Node merge(Node &first, Node &second) {
          return Node::merge(first, second);
        }

        void build(const vector<int>& array, size_t node, size_t left, size_t right) {
          if (right == left + 1) {
            if (node < tree.size() and left < array.size()) {
              tree[node] = Node::init(array[left]);
            }
            return;
          }
          auto middle = getMiddle(left, right);
          auto child = (node << 1) + 1;
          build(array, child, left, middle);
          build(array, child + 1, middle, right);
          tree[node] = merge(tree[child], tree[child + 1]);
        }

        void build(const vector<int>& array) {
          build(array, 0, 0, size);
        }
    public:
        explicit SegmentTree(const vector<int>& array) {
          init(array.size());
          build(array);
        }

        explicit SegmentTree(size_t n) {
          init(n);
        }

        void set(size_t i, int value) {
          size_t index = size - 1 + i;
          tree[index] = Node::init(value);
          while (index > 0) {
            index = (index - 1) >> 1;
            size_t child = (index << 1) + 1;
            tree[index] = merge(tree[child], tree[child + 1]);
          }
        }

        Node calc(ptrdiff_t left, ptrdiff_t right) {
          left += (ptrdiff_t) size - 1;
          right += (ptrdiff_t) size - 2;
          leftAccumulator = NEUTRAL;
          rightAccumulator = NEUTRAL;
          while (left <= right) {
            if (left & 1) {
              left = (left - 1) >> 1;
            } else {
              leftAccumulator = merge(leftAccumulator, tree[left]);
              left = (left - 1) >> 1;
              ++left;
            }
            if (right & 1) {
              rightAccumulator = merge(rightAccumulator, tree[right]);
              right = (right - 1) >> 1;
              --right;
            } else {
              right = (right - 1) >> 1;
            }
          }
          return merge(leftAccumulator, rightAccumulator);
        }

        int64 maxSeg() {
          return tree[0].seg;
        }

        void printTree() {
          for (auto el : tree) {
            //cout << el << ' ';
          }
          cout << '\n';
        }
    };
}

class Solver {
private:
    void solveOne() {
      int n, m;
      cin >> n >> m;
      vector<int> a(n);
      for (int i = 0; i < n; i++) {
        cin >> a[i];
      }
      using namespace SegTree;
      SegmentTree segmentTree = *new SegmentTree(a);
      cout << segmentTree.maxSeg() << '\n';
      while (m--) {
        int i, v;
        cin >> i >> v;
        segmentTree.set(i, v);
        cout << segmentTree.maxSeg() << '\n';
      }
    }
public:
    void solve() {
      int t = 1;
      // cin >> t;
      while (t--) {
        solveOne();
      }
    }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  //freopen("input.txt", "r", stdin);
  (new Solver)->solve();
  return 0;
}
