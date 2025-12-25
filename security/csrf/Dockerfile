FROM ghcr.io/graalvm/native-image-community:24.0.2 AS builder

WORKDIR /build

COPY . .

RUN chmod +x mvnw

RUN ./mvnw -Pnative native:compile -DskipTests 

FROM debian:bookworm-slim

RUN set -eux \
    && apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates zlib1g \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /build/target/csrf /app/runner

EXPOSE 8080

ENTRYPOINT ["/app/runner"]