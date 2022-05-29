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
    int value;

    Node(int value) {
      this->value = value;
    }

    static Node merge(const Node &first, const Node &second) {
      using namespace Utils;
      return {max(first.value,  second.value)};
    }

    static Node init(int value) {
      return {value};
    }
};

namespace SegTree {
    class SegmentTree {
    private:
        vector<int> tree;
        int size;
        const int NEUTRAL = INT32_MIN;

        void init(int len) {
          size = 1;
          while (size < len) {
            size <<= 1;
          }
          tree.assign((size << 1) - 1, NEUTRAL);
        }

        void build(const vector<int>& array, int nodeNow, int left, int right) {
          if (right == left + 1) {
            if (nodeNow < tree.size() and left < array.size()) {
              tree[nodeNow] = array[left];
            }
            return;
          }
          int middle = ((right - left) >> 1) + left;
          build(array, 2 * nodeNow + 1, left, middle), build(array, 2 * nodeNow + 2, middle, right);
          int child = (nodeNow << 1) + 1;
          tree[nodeNow] = ::max(tree[child], tree[child + 1]);
        }

        void build(const vector<int>& array) {
          build(array, 0, 0, size);
        }
    public:
        explicit SegmentTree(int len) {
          init(len);
        }

        explicit SegmentTree(const vector<int>& array) {
          init(array.size());
          build(array);
        }

        void set(int i, int value) {
          int index = size - 1 + i;
          tree[index] = value;
          while (index > 0) {
            index = (index - 1) >> 1;
            int child = (index << 1) + 1;
            tree[index] = ::max(tree[child], tree[child + 1]);
          }
        }

        int64 sum(int left, int right, int nodeNow, int leftNow, int rightNow) {
          if (leftNow >= left and rightNow <= right) {
            return tree[nodeNow];
          }
          if (leftNow >= right or rightNow <= left) {
            return NEUTRAL;
          }
          int middle = ((rightNow - leftNow) >> 1) + leftNow;
          int child = (nodeNow << 1) + 1;
          return ::max(sum(left, right, child, leftNow, middle),
                       sum(left, right, child + 1, middle, rightNow));
        }

        int rightSearch(int key, int left, int nodeNow, int leftNow, int rightNow) {
          if (tree[nodeNow] < key or rightNow <= left) {
            return -1;
          }
          if (rightNow == leftNow + 1) {
            return leftNow;
          }
          int middle = ((rightNow - leftNow) >> 1) + leftNow;
          int child = (nodeNow << 1) + 1;
          int result = rightSearch(key, left, child, leftNow, middle);
          if (result == -1) {
            result = rightSearch(key, left, child + 1, middle, rightNow);
          }
          return result;
        }

        int rightSearch(int key, int left) {
          return rightSearch(key, left, 0, 0, size);
        }

        int get(int i) {
          return tree[size - 1 + i];
        }

        int64 sum(int left, int right) {
          return sum(left, right, 0, 0, size);
        }

        void printTree() {
          ArraysUtils::printArray(tree);
        }
    };
}


template <typename T>
class Modular {
public:
    using Type = typename decay<decltype(T::value)>::type;

    constexpr Modular() : value() {}

    template <typename U>
    Modular(const U& x) {
      value = normalize(x);
    }

    template <typename U>
    static Type normalize(const U& x) {
      Type v = (-mod() <= x and x < mod()) ? static_cast<Type>(x) : static_cast<Type>(x % mod());
      if (v < 0) {
        v += mod();
      }
      return v;
    }

    const Type& operator()() const {
      return value;
    }

    template <typename U>
    explicit operator U() const {
      return static_cast<U>(value);
    }

    constexpr static Type mod() {
      return T::value; }

    Modular& operator+=(const Modular& other) {
      if ((value += other.value) >= mod()) {
        value -= mod();
      }
      return *this;
    }
private:
    Type value;
};

template <typename T> Modular<T> operator+(const Modular<T>& lhs, const Modular<T>& rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename T, typename U> Modular<T> operator+(const Modular<T>& lhs, U rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename T, typename U> Modular<T> operator+(U lhs, const Modular<T>& rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename U, typename T>
U& operator<<(U& stream, const Modular<T>& number) {
  return stream << number();
}

constexpr int MOD = 1000000007;
using Mint = Modular<std::integral_constant<decay<decltype(MOD)>::type, MOD>>;

class Solver {
private:
    void activation() {
      static bool start = false;
      if (!start) {
        srand(time(nullptr));
        start = true;
      }
    }

    size_t randomInRange(size_t left, size_t right) {
      activation();
      return left + rand() % ((right + 1) - left);
    }

    void solveOne() {
      typedef int itn;
      int n, m;
      cin >> n >> m;
      vector<int> a(n);
      for (int i = 0; i < n; i++) {
        cin >> a[i];
      }
      using namespace SegTree;
      SegmentTree segmentTree = *new SegmentTree(a);
      while (m--) {
        int i, j, k;
        cin >> i >> j >> k;
        if (i == 1) {
          segmentTree.set(j, k);
        } else {
          cout << segmentTree.rightSearch(j, k) << '\n';
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
