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

namespace SegTree {

    template <typename T, typename P>
    class SegmentTree {
    private:
        vector<T> tree;
        size_t size;
        T NEUTRAL;
        T leftAccumulator, rightAccumulator;

        void init(size_t n, T N) {
          this->NEUTRAL = N;
          size = 1;
          while (size < n) {
            size <<= 1;
          }
          tree.assign((size << 1) - 1, NEUTRAL);
        }

        size_t getMiddle(size_t left, size_t right) {
          return ((right - left) >> 1) + left;
        }

        T merge(T first, T second) {
          return first + second;
        }

        void build(const vector<P>& array, size_t node, size_t left, size_t right) {
          if (right == left + 1) {
            if (node < tree.size() and left < array.size()) {
              tree[node] = array[left];
            }
            return;
          }
          auto middle = getMiddle(left, right);
          auto child = (node << 1) + 1;
          build(array, child, left, middle);
          build(array, child + 1, middle, right);
          tree[node] = merge(tree[child], tree[child + 1]);
        }

        void build(const vector<P>& array) {
          build(array, 0, 0, size);
        }
    public:
        explicit SegmentTree(const vector<P>& array, T N) {
          init(array.size(), N);
          build(array);
        }

        explicit SegmentTree(size_t n, T N) {
          init(n, N);
        }

        void set(size_t i, T value) {
          size_t index = size - 1 + i;
          tree[index] = value;
          while (index > 0) {
            index = (index - 1) >> 1;
            size_t child = (index << 1) + 1;
            tree[index] = merge(tree[child], tree[child + 1]);
          }
        }

        T calc(ptrdiff_t left, ptrdiff_t right) {
          left += size - 1;
          right += size - 2;
          leftAccumulator = NEUTRAL;
          rightAccumulator = NEUTRAL;
          while (left <= right) {
            if (left & 1) {
              left = (left - 1) >> 1;
            } else {
              leftAccumulator += tree[left];
              left = (left - 1) >> 1;
              ++left;
            }
            if (right & 1) {
              rightAccumulator += tree[right];
              right = (right - 1) >> 1;
              --right;
            } else {
              right = (right - 1) >> 1;
            }
          }
          return leftAccumulator + rightAccumulator;
        }

        void printTree() {
          for (auto el : tree) {
            cout << el << ' ';
          }
          cout << '\n';
        }
    };
}

class Solver {
private:
    void solveOne() {
      int n;
      cin >> n;
      auto a = ArraysUtils::nextArray<int>(n);
      using namespace SegTree;
      SegmentTree<int64, int> segmentTree = *new SegmentTree<int64, int>(a, 0LL);
      string c;
      while (cin >> c) {
        int i, j;
        cin >> i >> j;
        --i;
        if (c == "set") {
          segmentTree.set(i, j);
        } else {
          cout << segmentTree.calc(i, j) << '\n';
        }
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
