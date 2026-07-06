import time

# BUBBLE SORT
def bubble_sort(arr_original):
    arr = arr_original.copy()
    comparaciones = 0
    intercambios = 0
    start_time = time.time()
    
    n = len(arr)
    for i in range(n):
        for j in range(0, n-i-1):
            comparaciones += 1
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]
                intercambios += 1
                
    end_time = time.time()
    return end_time - start_time, comparaciones, intercambios

# SELECTION SORT
def selection_sort(arr_original):
    arr = arr_original.copy()
    comparaciones = 0
    intercambios = 0
    start_time = time.time()
    
    n = len(arr)
    for i in range(n):
        min_idx = i
        for j in range(i + 1, n):
            comparaciones += 1
            if arr[j] < arr[min_idx]:
                min_idx = j
        if min_idx != i:
            arr[i], arr[min_idx] = arr[min_idx], arr[i]
            intercambios += 1
            
    end_time = time.time()
    return end_time - start_time, comparaciones, intercambios

# INSERTION SORT
def insertion_sort(arr_original):
    arr = arr_original.copy()
    comparaciones = 0
    intercambios = 0
    start_time = time.time()
    
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1
        while j >= 0:
            comparaciones += 1
            if arr[j] > key:
                arr[j + 1] = arr[j]
                intercambios += 1
                j -= 1
            else:
                break
        arr[j + 1] = key
        
    end_time = time.time()
    return end_time - start_time, comparaciones, intercambios

# MERGE SORT
def merge_sort(arr_original):
    comp_inter = [0, 0]
    
    def sort_recursivo(arr):
        if len(arr) <= 1:
            return arr
        mid = len(arr) // 2
        left = sort_recursivo(arr[:mid])
        right = sort_recursivo(arr[mid:])
        return merge(left, right)
        
    def merge(left, right):
        result = []
        i = j = 0
        while i < len(left) and j < len(right):
            comp_inter[0] += 1
            if left[i] <= right[j]:
                result.append(left[i])
                i += 1
            else:
                result.append(right[j])
                j += 1
                comp_inter[1] += 1
        result.extend(left[i:])
        result.extend(right[j:])
        return result

    start_time = time.time()
    sort_recursivo(arr_original.copy())
    end_time = time.time()
    return end_time - start_time, comp_inter[0], comp_inter[1]

# QUICK SORT
def quick_sort(arr_original):
    arr = arr_original.copy()
    comp_inter = [0, 0]
    
    def sort_recursivo(low, high):
        if low < high:
            pi = partition(low, high)
            sort_recursivo(low, pi - 1)
            sort_recursivo(pi + 1, high)
            
    def partition(low, high):
        pivot = arr[high]
        i = low - 1
        for j in range(low, high):
            comp_inter[0] += 1
            if arr[j] < pivot:
                i += 1
                arr[i], arr[j] = arr[j], arr[i]
                comp_inter[1] += 1
        arr[i + 1], arr[high] = arr[high], arr[i + 1]
        comp_inter[1] += 1
        return i + 1

    start_time = time.time()
    sort_recursivo(0, len(arr) - 1)
    end_time = time.time()
    return end_time - start_time, comp_inter[0], comp_inter[1]

# HEAP SORT
def heap_sort(arr_original):
    arr = arr_original.copy()
    comp_inter = [0, 0]
    
    def heapify(n, i):
        largest = i
        l = 2 * i + 1
        r = 2 * i + 2
        if l < n:
            comp_inter[0] += 1
            if arr[l] > arr[largest]: largest = l
        if r < n:
            comp_inter[0] += 1
            if arr[r] > arr[largest]: largest = r
        if largest != i:
            arr[i], arr[largest] = arr[largest], arr[i]
            comp_inter[1] += 1
            heapify(n, largest)

    start_time = time.time()
    n = len(arr)
    for i in range(n // 2 - 1, -1, -1):
        heapify(n, i)
    for i in range(n - 1, 0, -1):
        arr[i], arr[0] = arr[0], arr[i]
        comp_inter[1] += 1
        heapify(i, 0)
    end_time = time.time()
    return end_time - start_time, comp_inter[0], comp_inter[1]