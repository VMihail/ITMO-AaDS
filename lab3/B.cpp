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
      vector<vector<int>> map(n, vector<int>(m));
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          cin >> map[i][j];
        }
      }
      vector<vector<int>> dp(n, vector<int>(m));
      vector<vector<pair<int, int>>> way(n, vector<pair<int, int>>(m));
      way[0][0] = {0, 0};
      dp[0][0] = map[0][0];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (i == 0 && j == 0) {
            continue;
          }
          if (i == 0) {
            dp[i][j] = dp[i][j - 1] + map[i][j];
            way[i][j] = {i, j - 1};
          } else if (j == 0) {
            dp[i][j] = dp[i - 1][j] + map[i][j];
            way[i][j] = {i - 1, j};
          } else {
            dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]) + map[i][j];
            if (dp[i - 1][j] >= dp[i][j - 1]) {
              dp[i][j] = dp[i - 1][j];
              way[i][j] = {i - 1, j};
            } else {
              dp[i][j] = dp[i][j - 1];
              way[i][j] = {i, j - 1};
            }
            dp[i][j] += map[i][j];
          }
        }
      }
      cout << dp[n - 1][m - 1] << '\n';
      vector<unsigned char> result(n + m - 2);
      int e = n + m - 3;
      int i = n - 1, j = m - 1;
      while (true) {
        if (i == 0 and j == 0) {
          for (auto el : result) {
            cout << el;
          }
          return;
        }
        if (i == 0) {
          result[e--] = 'R';
          --j;
        } else if (j == 0) {
          result[e--] = 'D';
          --i;
        } else {
          int predI = way[i][j].first;
          if (i > predI) {
            result[e--] = 'D';
            --i;
          } else {
            result[e--] = 'R';
            --j;
          }
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
