package fleetoverview.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponseContainer<T> extends ApiResponse {
    private List<T> content;
    private Pagination pageable;

    public PagedResponseContainer(Page<T> page) {
        super(200, "success");
        this.content = page.getContent();
        this.pageable = new Pagination(page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination {
        private Long total;
        private Integer prev;
        private Integer next;
        private Integer current;

        public Pagination(Long total, int current, int size) {
            this.total = total;
            this.current = current;
            if (total > 0) {
                if (current > 0) {
                    this.prev = current - 1;
                }
                if ((total - (long) size * (current + 1)) > 0) {
                    if (total <= size) next = null;
                    else next = current + 1;
                }
            }
        }
    }
}
